package com.tuempresa.miapp.data.local

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.tuempresa.miapp.data.Repository
import com.tuempresa.miapp.data.RetrofitInstance
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.Dispatchers

class ShutdownReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_SHUTDOWN) return

        Log.d("ShutdownReceiver", "Apagado detectado — guardando borradores pendientes")

        // goAsync() da ~10 segundos extra antes de que el sistema mate el receiver
        val pendingResult = goAsync()

        // Room es local y ultra-rápido, runBlocking es seguro aquí
        runBlocking(Dispatchers.IO) {
            try {
                val db = AppDatabase.getInstance(context)
                // Solo sincronizamos los PENDING que ya están en Room.
                // No necesitamos hacer nada más: onPause() y onCleared() ya
                // habrán guardado el estado actual antes de llegar aquí.
                val pending = db.itemDao().getPendingSync()
                Log.d("ShutdownReceiver", "${pending.size} registros pendientes preservados en Room")
            } catch (e: Exception) {
                Log.e("ShutdownReceiver", "Error en apagado: ${e.message}")
            } finally {
                pendingResult.finish()
            }
        }
    }
}
