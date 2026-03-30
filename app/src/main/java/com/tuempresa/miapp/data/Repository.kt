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
    // OBSERVACIÓN REACTIVA (siempre desde Room, Room se llena desde servidor)
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
     * No toca los registros que están PENDING_* para no pisarlos.
     */
    suspend fun syncItemsFromServer() {
        try {
            val serverItems = api.getItems()
            val entities = serverItems.map {
                ItemEntity.fromItem(it, SyncStatus.SYNCED).copy(flagActivo = 1)
            }
            dao.upsertAll(entities)

            // Limpiar del Room los SYNCED que ya no existen en el servidor
            val serverIds = entities.map { it.serverId }
            if (serverIds.isNotEmpty()) {
                dao.deleteOldItemsNotInServer(serverIds)
            }
            Log.d("Repository", "syncItemsFromServer OK — ${entities.size} items")
        } catch (e: Exception) {
            Log.e("Repository", "syncItemsFromServer error: ${e.message}")
            // Sin internet → Room mantiene lo que tiene (pendientes + última copia)
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

    /**
     * Recorre todos los registros PENDING_* en Room y los envía al servidor.
     * Llamado al reconectar y en el SyncWorker de background.
     */
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
                    SyncStatus.SYNCED         -> Unit // no debería llegar aquí
                }
            } catch (e: Exception) {
                // Si falla uno, continúa con el siguiente. Se reintentará la próxima vez.
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
            // Sin internet: el registro sigue PENDING en Room, se reintenta al reconectar
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
    // CRUD: lógica offline-first simple
    //
    // Patrón para todas las operaciones:
    //   1. Guardar en Room con syncStatus = PENDING_*  (operación local inmediata)
    //   2. Intentar enviar al servidor
    //      - OK  → marcar como SYNCED en Room
    //      - FAIL (sin internet) → quedó como PENDING, se reintentará al reconectar
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun createItem(item: Item): retrofit2.Response<GenericResponse>? {
        // 1. Guardar en Room como pendiente (visible en UI inmediatamente)
        val entity = ItemEntity.fromItem(item, SyncStatus.PENDING_CREATE)
        val roomId = dao.upsert(entity).toInt()
        Log.d("Repository", "createItem → guardado en Room con roomId=$roomId")

        // 2. Intentar enviar al servidor
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
            null // La UI maneja null como "guardado offline"
        }
    }

    suspend fun updateItem(item: Item): retrofit2.Response<GenericResponse>? {
        // 1. Actualizar en Room como pendiente
        val existing = dao.getByServerId(item.id)
        val entity = ItemEntity.fromItem(
            item,
            SyncStatus.PENDING_UPDATE,
            roomId = existing?.roomId ?: 0
        )
        dao.upsert(entity)
        Log.d("Repository", "updateItem → actualizado en Room, serverId=${item.id}")

        // 2. Intentar enviar al servidor
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
        // 1. Marcar como eliminado en Room (desaparece de la lista activa)
        dao.softDeleteByServerId(serverId)

        // 2. Intentar enviar al servidor
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
        // 1. Restaurar en Room
        dao.restoreByServerId(serverId)

        // 2. Intentar en servidor
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

    /**
     * hardDelete requiere internet para eliminar en MySQL.
     * Sin internet: lanza excepcion controlada (NO crashea la app).
     * La UI debe mostrar un mensaje al usuario.
     */
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
        // 1. Actualizar en Room
        dao.updateEstadoPago(serverId, estadoPago)

        // 2. Intentar en servidor
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

    /**
     * Guarda un borrador en Room cuando la app se va a background.
     * No intenta ir al servidor.
     */
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
