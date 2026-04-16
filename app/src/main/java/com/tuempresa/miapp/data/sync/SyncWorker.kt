package com.tuempresa.miapp.data.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tuempresa.miapp.data.Repository
import com.tuempresa.miapp.data.RetrofitInstance
import com.tuempresa.miapp.data.SyncPreferences
import com.tuempresa.miapp.data.local.AppDatabase

class SyncWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", "Iniciando sincronización de fondo...")

        val db        = AppDatabase.getInstance(applicationContext)
        // ✅ FIX: SyncPreferences necesario para que Repository funcione correctamente
        val syncPrefs = SyncPreferences(applicationContext)
        val repo      = Repository(RetrofitInstance.api, db, syncPrefs)

        return try {
            // 1. Enviar cambios locales pendientes → servidor (offline → online)
            repo.syncPendingOperations()

            // 2. Bajar estado del servidor → Room (activos + papelera)
            repo.syncItemsFromServer()
            // ✅ FIX: la papelera también se sincroniza en background.
            //         Antes solo se sincronizaban los items activos, por lo que
            //         eliminaciones hechas en otro dispositivo no aparecían
            //         en la papelera hasta que el usuario la abría manualmente.
            repo.syncTrashFromServer()

            Log.d("SyncWorker", "Sincronización exitosa")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Fallo en la sincronización: ${e.message}")
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }
}
