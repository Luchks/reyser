package com.tuempresa.miapp.data.local

import android.util.Log
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlin.math.log
@Dao
interface ItemDao {
    @Query("SELECT * FROM items WHERE flagActivo = 1 ORDER BY fecha DESC, horaInicio DESC")
    fun observeActiveItems(): Flow<List<ItemEntity>>
    @Query("SELECT * FROM items WHERE flagActivo = 0 ORDER BY fecha DESC")
    fun observeTrashItems(): Flow<List<ItemEntity>>
    @Query("SELECT * FROM items WHERE roomId = :roomId")
    suspend fun getByRoomId(roomId: Int): ItemEntity?
    @Query("SELECT * FROM items WHERE serverId = :serverId LIMIT 1")
    suspend fun getByServerId(serverId: Int): ItemEntity?
    @Query("SELECT * FROM items WHERE syncStatus != 'SYNCED'")
    suspend fun getPendingSync(): List<ItemEntity>

 
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ItemEntity): Long

    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(entities: List<ItemEntity>)

    @Query("""
        UPDATE items 
        SET serverId = :serverId, syncStatus = 'SYNCED'
        WHERE roomId = :roomId
    """)
    suspend fun markSynced(roomId: Int, serverId: Int)
    @Query("UPDATE items SET syncStatus = 'SYNCED' WHERE roomId = :roomId")
    suspend fun markSyncedByRoomId(roomId: Int)

    @Query("""
        UPDATE items 
        SET flagActivo = 0, syncStatus = 'PENDING_DELETE' 
        WHERE serverId = :serverId
    """)
    suspend fun softDeleteByServerId(serverId: Int)

    @Query("""
        UPDATE items 
        SET flagActivo = 1, syncStatus = 'PENDING_UPDATE'
        WHERE serverId = :serverId
    """)
    suspend fun restoreByServerId(serverId: Int)

    @Query("""
        UPDATE items 
        SET estadoPago = :estadoPago, syncStatus = 'PENDING_UPDATE'
        WHERE serverId = :serverId
    """)
    suspend fun updateEstadoPago(serverId: Int, estadoPago: String)

    @Query("DELETE FROM items WHERE serverId = :serverId")
    suspend fun hardDeleteByServerId(serverId: Int)

    @Query("DELETE FROM items WHERE roomId = :roomId")
    suspend fun hardDeleteByRoomId(roomId: Int)

    @Query("DELETE FROM items WHERE flagActivo = 0 AND syncStatus = 'SYNCED'")
    suspend fun deleteAllTrash()

    @Transaction
    suspend fun replaceActiveCache(serverItems: List<ItemEntity>) {
        try {
            // Pon el Breakpoint en la línea de abajo
            Log.d("DEBUG_SYNC", "1. Iniciando limpieza de caché local...")
            deleteAllSynced()
            
            Log.d("DEBUG_SYNC", "2. Insertando ${serverItems.size} nuevos registros desde el servidor")
            upsertAll(serverItems)
            
            Log.d("DEBUG_SYNC", "3. Reemplazo completado con éxito")
        } catch (e: Exception) {
            // AQUÍ ATRAPAS EL ERROR
            Log.e("DEBUG_SYNC", "ERROR CRÍTICO en replaceActiveCache: ${e.message}")
            throw e // Re-lanzamos para que el Repository también sepa que falló
        }
    }


    @Query("DELETE FROM items WHERE syncStatus = 'SYNCED' AND flagActivo = 1")
    suspend fun deleteAllSynced()

    @Query("""
        DELETE FROM items 
        WHERE serverId NOT IN (:serverIds) 
          AND syncStatus = 'SYNCED' 
          AND flagActivo = 1
    """)
    suspend fun deleteOldItemsNotInServer(serverIds: List<Int>)
    @Query("""
        DELETE FROM items 
        WHERE serverId NOT IN (:serverIds) 
          AND syncStatus = 'SYNCED' 
          AND flagActivo = 0
    """)
    suspend fun deleteOldTrashNotInServer(serverIds: List<Int>)
    @Query("DELETE FROM items WHERE flagActivo = 0 AND syncStatus = 'SYNCED'")
    suspend fun deleteAllSyncedTrash()

}
