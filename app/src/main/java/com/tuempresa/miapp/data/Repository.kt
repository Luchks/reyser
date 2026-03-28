package com.tuempresa.miapp.data

// ═══════════════════════════════════════════════════════════════════════════════
//  Repository.kt  —  Lógica offline-first SIMPLE
//
//  REGLA CENTRAL:
//  ┌─────────────────────────────────────────────────────────────────────────┐
//  │  CON INTERNET  → todo va directo a MySQL (via API PHP)                  │
//  │  SIN INTERNET  → se guarda en Room como cola de pendientes              │
//  │  AL RECONECTAR → se vacía la cola hacia MySQL y Room queda limpio       │
//  └─────────────────────────────────────────────────────────────────────────┘
//
//  Room NO es caché permanente. Es solo una cola temporal para operaciones
//  offline. La fuente de verdad siempre es MySQL.
// ═══════════════════════════════════════════════════════════════════════════════

import android.util.Log
import com.tuempresa.miapp.data.local.AppDatabase
import com.tuempresa.miapp.data.local.ItemDao
import com.tuempresa.miapp.data.local.ItemEntity
import com.tuempresa.miapp.data.local.SyncStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

private const val TAG = "Repository"

class Repository(
    private val api: ApiService,
    private val db: AppDatabase,
    private val syncPrefs: SyncPreferences
) {
    private val dao: ItemDao = db.itemDao()

    // ─────────────────────────────────────────────────────────────────────────
    // ESTADO DE CONECTIVIDAD
    // El ViewModel o MainActivity actualizan esto cuando cambia la red.
    // ─────────────────────────────────────────────────────────────────────────

    private val _isOnline = MutableStateFlow(false)
    val isOnline = _isOnline.asStateFlow()

    fun setOnline(online: Boolean) {
        _isOnline.value = online
    }

    // ─────────────────────────────────────────────────────────────────────────
    // OBSERVACIÓN REACTIVA
    //
    // Con internet  → la UI muestra datos descargados del servidor (guardados
    //                 temporalmente en Room con syncStatus = SYNCED)
    // Sin internet  → la UI muestra solo los pendientes de Room
    // ─────────────────────────────────────────────────────────────────────────

    fun observeItems(): Flow<List<Item>> =
        dao.observeActiveItems().map { list -> list.map { it.toItem() } }

    fun observeTrash(): Flow<List<Item>> =
        dao.observeTrashItems().map { list -> list.map { it.toItem() } }

    // ─────────────────────────────────────────────────────────────────────────
    // SINCRONIZACIÓN SERVIDOR → ROOM  (descarga)
    //
    // Solo se llama cuando hay internet. Trae los datos del servidor,
    // los pisa en Room (reemplaza los SYNCED) y deja intactos los PENDING_*.
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun syncItemsFromServer() {
        try {
            val serverItems = api.getItems()
            val entities = serverItems.map { item ->
                // Buscar si ya existe en Room para preservar el roomId
                val existing = dao.getByServerId(item.id)
                ItemEntity.fromItem(item, SyncStatus.SYNCED, roomId = existing?.roomId ?: 0)
                    .copy(flagActivo = 1)
            }
            // Insertar/actualizar los que vienen del servidor
            dao.upsertAll(entities)

            // Eliminar de Room los que el servidor ya no tiene
            // (solo los SYNCED — los PENDING_* se preservan)
            val serverIds = entities.map { it.serverId }
            if (serverIds.isNotEmpty()) {
                dao.deleteOldItemsNotInServer(serverIds)
            }

            Log.d(TAG, "syncItemsFromServer OK — ${entities.size} items")
        } catch (e: Exception) {
            Log.e(TAG, "syncItemsFromServer ERROR: ${e.message}")
            throw e
        }
    }

    suspend fun syncTrashFromServer() {
        try {
            val serverTrash = api.getTrash()
            val entities = serverTrash.map { item ->
                val existing = dao.getByServerId(item.id)
                ItemEntity.fromItem(item, SyncStatus.SYNCED, roomId = existing?.roomId ?: 0)
                    .copy(flagActivo = 0)
            }
            dao.upsertAll(entities)

            val serverIds = entities.map { it.serverId }
            if (serverIds.isNotEmpty()) {
                dao.deleteOldTrashNotInServer(serverIds)
            } else {
                dao.deleteAllSyncedTrash()
            }

            Log.d(TAG, "syncTrashFromServer OK — ${entities.size} en papelera")
        } catch (e: Exception) {
            Log.e(TAG, "syncTrashFromServer ERROR: ${e.message}")
            throw e
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CHECK DE CAMBIOS (polling)
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun checkForUpdatesAndSync() {
        try {
            val response = api.checkUpdates()
            if (response.success && response.signature != syncPrefs.lastSignature) {
                Log.d(TAG, "Cambio detectado (firma: ${response.signature}) — sincronizando...")
                syncItemsFromServer()
                syncTrashFromServer()
                syncPrefs.lastSignature = response.signature
            }
        } catch (e: Exception) {
            Log.e(TAG, "checkForUpdatesAndSync ERROR: ${e.message}")
            throw e
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SINCRONIZACIÓN PENDIENTES → SERVIDOR  (subida de cola offline)
    //
    // Recorre Room buscando registros con syncStatus != SYNCED
    // y los envía al servidor en orden. Si el servidor confirma, los marca
    // SYNCED. Si falla, se quedan en Room para el próximo intento.
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun syncPendingOperations() {
        val pending = dao.getPendingSync()
        if (pending.isEmpty()) {
            Log.d(TAG, "Sin pendientes en cola")
            return
        }

        Log.d(TAG, "Sincronizando ${pending.size} operaciones pendientes...")

        for (entity in pending) {
            try {
                when (SyncStatus.valueOf(entity.syncStatus)) {
                    SyncStatus.PENDING_CREATE -> handlePendingCreate(entity)
                    SyncStatus.PENDING_UPDATE -> handlePendingUpdate(entity)
                    SyncStatus.PENDING_DELETE -> handlePendingDelete(entity)
                    SyncStatus.SYNCED         -> Unit // no debería aparecer aquí
                }
            } catch (e: Exception) {
                // Un fallo individual no detiene el resto de la cola
                Log.e(TAG, "Error sincronizando roomId=${entity.roomId}: ${e.message}")
            }
        }
    }

    private suspend fun handlePendingCreate(entity: ItemEntity) {
        val response = api.createItem(entity.toItem())
        if (response.isSuccessful && response.body()?.success == true) {
            val serverId = response.body()?.id ?: return
            // Actualizar Room con el ID real del servidor y marcar SYNCED
            dao.markSynced(entity.roomId, serverId)
            Log.d(TAG, "PENDING_CREATE resuelto → serverId=$serverId")
        } else {
            Log.w(TAG, "PENDING_CREATE falló para roomId=${entity.roomId} — se reintentará")
        }
    }

    private suspend fun handlePendingUpdate(entity: ItemEntity) {
        if (entity.serverId == 0) {
            // Nunca llegó al servidor, convertir a CREATE
            handlePendingCreate(entity.copy(syncStatus = SyncStatus.PENDING_CREATE.name))
            return
        }
        val response = api.updateItem(entity.toItem())
        if (response.isSuccessful && response.body()?.success == true) {
            dao.markSyncedByRoomId(entity.roomId)
            Log.d(TAG, "PENDING_UPDATE resuelto → roomId=${entity.roomId}")
        } else {
            Log.w(TAG, "PENDING_UPDATE falló para roomId=${entity.roomId} — se reintentará")
        }
    }

    private suspend fun handlePendingDelete(entity: ItemEntity) {
        if (entity.serverId == 0) {
            // Nunca llegó al servidor, borrar de Room directamente
            dao.hardDeleteByRoomId(entity.roomId)
            Log.d(TAG, "PENDING_DELETE (sin serverId) — eliminado de Room")
            return
        }
        val response = api.softDelete(entity.serverId)
        if (response.isSuccessful && response.body()?.success == true) {
            dao.markSyncedByRoomId(entity.roomId)
            Log.d(TAG, "PENDING_DELETE resuelto → serverId=${entity.serverId}")
        } else {
            Log.w(TAG, "PENDING_DELETE falló para serverId=${entity.serverId} — se reintentará")
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CRUD — Lógica offline-first
    //
    // Cada operación sigue el mismo patrón:
    //   1. Guardar en Room con syncStatus = PENDING_*
    //   2. Intentar enviar al servidor
    //   3a. Si OK → marcar SYNCED en Room
    //   3b. Si falla (sin internet) → quedar como PENDING para sync posterior
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun createItem(item: Item): retrofit2.Response<GenericResponse>? {
        // 1. Guardar en Room como pendiente
        val entity = ItemEntity.fromItem(item, SyncStatus.PENDING_CREATE)
        val roomId = dao.upsert(entity).toInt()
        Log.d(TAG, "createItem: guardado en Room con roomId=$roomId")

        // 2. Intentar enviar al servidor
        return try {
            val response = api.createItem(item)
            if (response.isSuccessful && response.body()?.success == true) {
                val serverId = response.body()?.id
                if (serverId != null) {
                    dao.markSynced(roomId, serverId)
                    Log.d(TAG, "createItem: sincronizado → serverId=$serverId")
                }
            }
            response
        } catch (e: Exception) {
            // Sin internet — quedó en Room como PENDING_CREATE para sync posterior
            Log.w(TAG, "createItem OFFLINE — quedará pendiente: ${e.message}")
            null
        }
    }

    suspend fun updateItem(item: Item): retrofit2.Response<GenericResponse>? {
        // 1. Actualizar Room como pendiente
        val existing = dao.getByServerId(item.id)
        val entity = ItemEntity.fromItem(
            item,
            SyncStatus.PENDING_UPDATE,
            roomId = existing?.roomId ?: 0
        )
        dao.upsert(entity)
        Log.d(TAG, "updateItem: guardado en Room para serverId=${item.id}")

        // 2. Intentar enviar al servidor
        return try {
            val response = api.updateItem(item)
            if (response.isSuccessful && response.body()?.success == true) {
                val roomId = existing?.roomId
                    ?: dao.getByServerId(item.id)?.roomId
                    ?: return response
                dao.markSyncedByRoomId(roomId)
                Log.d(TAG, "updateItem: sincronizado → serverId=${item.id}")
            }
            response
        } catch (e: Exception) {
            Log.w(TAG, "updateItem OFFLINE — quedará pendiente: ${e.message}")
            null
        }
    }

    suspend fun softDelete(id: Int) {
        // 1. Marcar en Room como pendiente de borrado
        dao.softDeleteByServerId(id)
        Log.d(TAG, "softDelete: marcado en Room para serverId=$id")

        // 2. Intentar en el servidor
        try {
            val response = api.softDelete(id)
            if (response.isSuccessful && response.body()?.success == true) {
                dao.getByServerId(id)?.let { dao.markSyncedByRoomId(it.roomId) }
                Log.d(TAG, "softDelete: sincronizado → serverId=$id")
            }
        } catch (e: Exception) {
            Log.w(TAG, "softDelete OFFLINE — quedará pendiente: ${e.message}")
        }
    }

    suspend fun restore(id: Int) {
        // 1. Restaurar en Room
        dao.restoreByServerId(id)

        // 2. Intentar en el servidor
        try {
            val response = api.restore(id)
            if (response.isSuccessful && response.body()?.success == true) {
                dao.getByServerId(id)?.let { dao.markSyncedByRoomId(it.roomId) }
                Log.d(TAG, "restore: sincronizado → serverId=$id")
            }
        } catch (e: Exception) {
            Log.w(TAG, "restore OFFLINE — quedará pendiente: ${e.message}")
        }
    }

    suspend fun hardDelete(id: Int) {
        // hard delete requiere internet (no tiene lógica offline porque
        // si nunca llega al servidor, el registro ya no existe en ningún lado)
        try {
            val response = api.hardDelete(id)
            if (response.isSuccessful) {
                dao.hardDeleteByServerId(id)
                Log.d(TAG, "hardDelete: eliminado → serverId=$id")
            }
        } catch (e: Exception) {
            Log.e(TAG, "hardDelete ERROR — requiere internet: ${e.message}")
            throw e // Re-lanzar para que el ViewModel informe al usuario
        }
    }

    suspend fun updateEstadoPago(id: Int, estadoPago: String): retrofit2.Response<GenericResponse>? {
        // 1. Actualizar en Room como pendiente
        dao.updateEstadoPago(id, estadoPago)
        Log.d(TAG, "updateEstadoPago: guardado en Room para serverId=$id")

        // 2. Intentar en el servidor
        return try {
            val response = api.updateEstadoPago(UpdateEstadoPagoRequest(id, estadoPago))
            if (response.isSuccessful && response.body()?.success == true) {
                dao.getByServerId(id)?.let { dao.markSyncedByRoomId(it.roomId) }
                Log.d(TAG, "updateEstadoPago: sincronizado → serverId=$id")
            }
            response
        } catch (e: Exception) {
            Log.w(TAG, "updateEstadoPago OFFLINE — quedará pendiente: ${e.message}")
            null
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // BORRADOR (guardado local sin intentar el servidor)
    // Solo para onCleared() / autoSave antes de cerrar la app
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun saveDraftToRoom(item: Item, currentItemId: Int) {
        if (currentItemId == -1) {
            dao.upsert(ItemEntity.fromItem(item, SyncStatus.PENDING_CREATE))
        } else {
            val existing = dao.getByServerId(currentItemId)
            dao.upsert(
                ItemEntity.fromItem(item, SyncStatus.PENDING_UPDATE, roomId = existing?.roomId ?: 0)
            )
        }
        Log.d(TAG, "saveDraftToRoom: borrador guardado para itemId=$currentItemId")
    }

    // ─────────────────────────────────────────────────────────────────────────
    // LOGIN
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun login(username: String, password: String) = api.login(username, password)
}
