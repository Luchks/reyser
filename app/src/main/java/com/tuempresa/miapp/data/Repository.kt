package com.tuempresa.miapp.data

import android.util.Log
import com.tuempresa.miapp.data.local.AppDatabase
import com.tuempresa.miapp.data.local.ItemDao
import com.tuempresa.miapp.data.local.ItemEntity
import com.tuempresa.miapp.data.local.SyncStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * ARQUITECTURA OFFLINE-FIRST SIMPLE
 * ══════════════════════════════════
 *
 * CON INTERNET:
 *   - INSERT / UPDATE / DELETE → van directo a MySQL vía PHP
 *   - Los datos que se muestran vienen del servidor (sincronizados en Room)
 *
 * SIN INTERNET:
 *   - Las operaciones se guardan en Room con syncStatus = PENDING_*
 *   - La UI muestra lo que hay en Room (última copia conocida + pendientes)
 *
 * AL RECONECTAR:
 *   - syncPendingOperations() recorre Room, envía cada pendiente al servidor
 *   - Si el servidor responde OK → marca como SYNCED (o borra si era DELETE)
 *   - Luego baja el estado fresco del servidor
 *
 * Room NO es caché permanente. Es una cola temporal.
 * MySQL es la fuente de verdad.
 */
class Repository(
    private val api: ApiService,
    private val db: AppDatabase,
    private val syncPrefs: SyncPreferences
) {
    private val dao: ItemDao = db.itemDao()

    // ─────────────────────────────────────────────────────────────────────────
    // OBSERVACIÓN REACTIVA
    // ─────────────────────────────────────────────────────────────────────────

    fun observeItems(): Flow<List<Item>> =
        dao.observeActiveItems().map { list -> list.map { it.toItem() } }

    fun observeTrash(): Flow<List<Item>> =
        dao.observeTrashItems().map { list -> list.map { it.toItem() } }

    // ─────────────────────────────────────────────────────────────────────────
    // SINCRONIZACIÓN: servidor → Room
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Baja los items activos del servidor y los guarda en Room (SYNCED).
     *
     * FIX BUG 2 — Antes hacía upsertAll() de todo sin distinción, lo que
     * podía pisar registros PENDING_CREATE creados offline en otro celular
     * (que tienen serverId = 0) causando duplicados.
     *
     * Ahora filtra los items del servidor cuyo codigoReserva coincide con
     * algún PENDING_CREATE local, y los excluye del upsert hasta que ese
     * pendiente sea sincronizado y reciba su serverId real.
     */
    suspend fun syncItemsFromServer() {
        try {
            val serverItems = api.getItems()
            val entities = serverItems.map {
                ItemEntity.fromItem(it, SyncStatus.SYNCED).copy(flagActivo = 1)
            }

            // Obtener los codigoReserva de registros PENDING_CREATE locales.
            // Estos son registros creados offline que aún no tienen serverId.
            // No se deben pisar con datos del servidor hasta que se sincronicen.
            val pendingCodigos = dao.getPendingSync()
                .filter { it.syncStatus == SyncStatus.PENDING_CREATE.name }
                .map { it.codigoReserva }
                .toSet()

            // Solo hacer upsert de los registros del servidor que no colisionen
            // con un PENDING_CREATE local sin sincronizar
            val safeToUpsert = if (pendingCodigos.isEmpty()) {
                entities
            } else {
                entities.filter { it.codigoReserva !in pendingCodigos }
            }

            dao.upsertAll(safeToUpsert)

            // Limpiar del Room los SYNCED que ya no existen en el servidor
            val serverIds = entities.map { it.serverId }
            if (serverIds.isNotEmpty()) {
                dao.deleteOldItemsNotInServer(serverIds)
            }

            Log.d("Repository", "syncItemsFromServer OK — ${entities.size} items (${entities.size - safeToUpsert.size} omitidos por PENDING_CREATE local)")
        } catch (e: Exception) {
            Log.e("Repository", "syncItemsFromServer error: ${e.message}")
        }
    }

    suspend fun syncTrashFromServer() {
        try {
            val serverTrash = api.getTrash()
            val entities = serverTrash.map {
                ItemEntity.fromItem(it, SyncStatus.SYNCED).copy(flagActivo = 0)
            }
            dao.upsertAll(entities)

            val serverIds = entities.map { it.serverId }
            if (serverIds.isNotEmpty()) {
                dao.deleteOldTrashNotInServer(serverIds)
            } else {
                dao.deleteAllSyncedTrash()
            }
            Log.d("Repository", "syncTrashFromServer OK — ${entities.size} en papelera")
        } catch (e: Exception) {
            Log.e("Repository", "syncTrashFromServer error: ${e.message}")
        }
    }

    /**
     * Polling inteligente por firma: solo descarga si el servidor cambió.
     */
    suspend fun checkForUpdatesAndSync() {
        try {
            val response = api.checkUpdates()
            if (response.success && response.signature != syncPrefs.lastSignature) {
                Log.d("Repository", "Cambio detectado → sincronizando")
                syncItemsFromServer()
                syncTrashFromServer()
                syncPrefs.lastSignature = response.signature
            }
        } catch (e: Exception) {
            Log.e("Repository", "checkForUpdatesAndSync error: ${e.message}")
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SINCRONIZACIÓN: Room (pendientes) → servidor
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun syncPendingOperations() {
        val pending = dao.getPendingSync()
        if (pending.isEmpty()) {
            Log.d("Repository", "No hay operaciones pendientes")
            return
        }
        Log.d("Repository", "Sincronizando ${pending.size} operaciones pendientes...")

        for (entity in pending) {
            try {
                when (SyncStatus.valueOf(entity.syncStatus)) {
                    SyncStatus.PENDING_CREATE -> handlePendingCreate(entity)
                    SyncStatus.PENDING_UPDATE -> handlePendingUpdate(entity)
                    SyncStatus.PENDING_DELETE -> handlePendingDelete(entity)
                    SyncStatus.SYNCED         -> Unit
                }
            } catch (e: Exception) {
                Log.e("Repository", "Fallo sincronizando roomId=${entity.roomId}: ${e.message}")
            }
        }
    }

    private suspend fun handlePendingCreate(entity: ItemEntity) {
        try {
            val response = api.createItem(entity.toItem())
            if (response.isSuccessful && response.body()?.success == true) {
                val serverId = response.body()?.id ?: return
                dao.markSynced(entity.roomId, serverId)
                Log.d("Repository", "PENDING_CREATE OK, serverId=$serverId")
            }
        } catch (e: java.io.IOException) {
            Log.w("Repository", "PENDING_CREATE sin internet: ${e.message}")
        }
    }

    private suspend fun handlePendingUpdate(entity: ItemEntity) {
        if (entity.serverId == 0) {
            handlePendingCreate(entity)
            return
        }
        try {
            val response = api.updateItem(entity.toItem())
            if (response.isSuccessful && response.body()?.success == true) {
                dao.markSyncedByRoomId(entity.roomId)
                Log.d("Repository", "PENDING_UPDATE OK, roomId=${entity.roomId}")
            }
        } catch (e: java.io.IOException) {
            Log.w("Repository", "PENDING_UPDATE sin internet: ${e.message}")
        }
    }

    private suspend fun handlePendingDelete(entity: ItemEntity) {
        if (entity.serverId == 0) {
            dao.hardDeleteByRoomId(entity.roomId)
            return
        }
        try {
            val response = api.softDelete(entity.serverId)
            if (response.isSuccessful && response.body()?.success == true) {
                dao.markSyncedByRoomId(entity.roomId)
                Log.d("Repository", "PENDING_DELETE OK, serverId=${entity.serverId}")
            }
        } catch (e: java.io.IOException) {
            Log.w("Repository", "PENDING_DELETE sin internet: ${e.message}")
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CRUD: lógica offline-first
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun createItem(item: Item): retrofit2.Response<GenericResponse>? {
        val entity = ItemEntity.fromItem(item, SyncStatus.PENDING_CREATE)
        val roomId = dao.upsert(entity).toInt()
        Log.d("Repository", "createItem → guardado en Room con roomId=$roomId")

        return try {
            val response = api.createItem(item)
            if (response.isSuccessful && response.body()?.success == true) {
                val serverId = response.body()?.id
                if (serverId != null) {
                    dao.markSynced(roomId, serverId)
                    Log.d("Repository", "createItem → sincronizado con MySQL, serverId=$serverId")
                }
            }
            response
        } catch (e: Exception) {
            Log.w("Repository", "createItem → sin internet, quedó como PENDING_CREATE")
            null
        }
    }

    suspend fun updateItem(item: Item): retrofit2.Response<GenericResponse>? {
        val existing = dao.getByServerId(item.id)
        val entity = ItemEntity.fromItem(
            item,
            SyncStatus.PENDING_UPDATE,
            roomId = existing?.roomId ?: 0
        )
        dao.upsert(entity)
        Log.d("Repository", "updateItem → actualizado en Room, serverId=${item.id}")

        return try {
            val response = api.updateItem(item)
            if (response.isSuccessful && response.body()?.success == true) {
                val roomId = existing?.roomId ?: dao.getByServerId(item.id)?.roomId ?: return response
                dao.markSyncedByRoomId(roomId)
                Log.d("Repository", "updateItem → sincronizado con MySQL")
            }
            response
        } catch (e: Exception) {
            Log.w("Repository", "updateItem → sin internet, quedó como PENDING_UPDATE")
            null
        }
    }

    suspend fun softDelete(serverId: Int) {
        dao.softDeleteByServerId(serverId)

        try {
            val response = api.softDelete(serverId)
            if (response.isSuccessful && response.body()?.success == true) {
                dao.getByServerId(serverId)?.let { dao.markSyncedByRoomId(it.roomId) }
                Log.d("Repository", "softDelete → sincronizado con MySQL, id=$serverId")
            }
        } catch (e: Exception) {
            Log.w("Repository", "softDelete → sin internet, quedó como PENDING_DELETE")
        }
    }

    suspend fun restore(serverId: Int) {
        dao.restoreByServerId(serverId)

        try {
            val response = api.restore(serverId)
            if (response.isSuccessful && response.body()?.success == true) {
                dao.getByServerId(serverId)?.let { dao.markSyncedByRoomId(it.roomId) }
                Log.d("Repository", "restore → sincronizado con MySQL, id=$serverId")
            }
        } catch (e: Exception) {
            Log.w("Repository", "restore → sin internet, quedó como PENDING_UPDATE")
        }
    }

    suspend fun hardDelete(serverId: Int) {
        try {
            val response = api.hardDelete(serverId)
            if (response.isSuccessful) {
                dao.hardDeleteByServerId(serverId)
                Log.d("Repository", "hardDelete OK, id=$serverId")
            } else {
                throw Exception("El servidor rechazo la eliminacion definitiva")
            }
        } catch (e: java.io.IOException) {
            Log.w("Repository", "hardDelete sin internet: ${e.message}")
            throw Exception("Sin conexion. Conectate a internet para eliminar definitivamente.")
        }
    }

    suspend fun updateEstadoPago(serverId: Int, estadoPago: String): retrofit2.Response<GenericResponse>? {
        dao.updateEstadoPago(serverId, estadoPago)

        return try {
            val response = api.updateEstadoPago(UpdateEstadoPagoRequest(serverId, estadoPago))
            if (response.isSuccessful && response.body()?.success == true) {
                dao.getByServerId(serverId)?.let { dao.markSyncedByRoomId(it.roomId) }
                Log.d("Repository", "updateEstadoPago → sincronizado con MySQL")
            }
            response
        } catch (e: Exception) {
            Log.w("Repository", "updateEstadoPago → sin internet, quedó como PENDING_UPDATE")
            null
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GUARDADO OPTIMISTA: Room primero, servidor después
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun saveToRoomImmediate(item: Item, currentItemId: Int): Long {
        return if (currentItemId == -1) {
            val entity = ItemEntity.fromItem(item, SyncStatus.PENDING_CREATE)
            dao.upsert(entity)
        } else {
            val existing = dao.getByServerId(currentItemId)
            val entity = ItemEntity.fromItem(
                item,
                SyncStatus.PENDING_UPDATE,
                roomId = existing?.roomId ?: 0
            )
            dao.upsert(entity)
        }
    }

    /**
     * Intenta sincronizar una reserva NUEVA al servidor.
     *
     * FIX BUG 2 — La versión anterior buscaba el registro local con
     * dao.getByServerId(0), que podía devolver cualquier PENDING_CREATE
     * (no necesariamente el que acabábamos de guardar), y además nunca
     * llamaba a dao.markSynced(), dejando el registro con serverId = 0
     * para siempre y causando duplicados cuando otro celular sincronizaba.
     *
     * Ahora identifica el registro correcto por codigoReserva (único por
     * diseño de negocio) y llama a markSynced() con el roomId preciso.
     */
    suspend fun syncNewItemToServer(item: Item): retrofit2.Response<GenericResponse>? {
        return try {
            val response = api.createItem(item)
            if (response.isSuccessful && response.body()?.success == true) {
                val serverId = response.body()?.id
                if (serverId != null) {
                    // Identificar el registro local por codigoReserva, que es
                    // único y se genera antes del guardado → nunca es ambiguo
                    val entity = dao.getByCodigoReserva(item.codigoReserva)
                    if (entity != null) {
                        dao.markSynced(entity.roomId, serverId)
                        Log.d("Repository", "syncNewItemToServer OK — roomId=${entity.roomId} → serverId=$serverId")
                    } else {
                        Log.w("Repository", "syncNewItemToServer: no se encontró el registro local con codigoReserva=${item.codigoReserva}")
                    }
                }
            }
            response
        } catch (e: java.io.IOException) {
            Log.w("Repository", "syncNewItemToServer sin internet, quedo PENDING")
            null
        }
    }

    suspend fun syncUpdatedItemToServer(item: Item): retrofit2.Response<GenericResponse>? {
        return try {
            val response = api.updateItem(item)
            if (response.isSuccessful && response.body()?.success == true) {
                val existing = dao.getByServerId(item.id)
                existing?.let { dao.markSyncedByRoomId(it.roomId) }
                Log.d("Repository", "syncUpdatedItemToServer OK, id=${item.id}")
            }
            response
        } catch (e: java.io.IOException) {
            Log.w("Repository", "syncUpdatedItemToServer sin internet, quedo PENDING")
            null
        }
    }

    suspend fun saveDraftToRoom(item: Item, currentItemId: Int) {
        if (currentItemId == -1) {
            val entity = ItemEntity.fromItem(item, SyncStatus.PENDING_CREATE)
            dao.upsert(entity)
        } else {
            val existing = dao.getByServerId(currentItemId)
            val entity = ItemEntity.fromItem(
                item,
                SyncStatus.PENDING_UPDATE,
                roomId = existing?.roomId ?: 0
            )
            dao.upsert(entity)
        }
        Log.d("Repository", "saveDraftToRoom → borrador guardado para sync posterior")
    }

    suspend fun login(username: String, password: String) = api.login(username, password)
}
