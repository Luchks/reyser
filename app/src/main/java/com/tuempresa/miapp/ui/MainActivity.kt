package com.tuempresa.miapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.libraries.places.api.Places
import com.tuempresa.miapp.data.Repository
import com.tuempresa.miapp.data.RetrofitInstance
import com.tuempresa.miapp.data.SyncPreferences
import com.tuempresa.miapp.data.local.AppDatabase
import com.tuempresa.miapp.data.sync.NetworkConnectivityObserver
import com.tuempresa.miapp.data.sync.SyncWorker
import com.tuempresa.miapp.ui.navigation.NavGraph
import com.tuempresa.miapp.viewmodel.MainViewModel
import com.tuempresa.miapp.viewmodel.MainViewModelFactory
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private lateinit var vm: MainViewModel

    // ✅ NUEVO: observador de red para sincronizar pendientes al recuperar internet
    private lateinit var networkObserver: NetworkConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Google Places
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyCwfkBlSaH4CO9oDGJqF4Qt1YfdJR7L2PQ")
        }

        // ✅ NUEVO: registrar el observador de red una sola vez en onCreate.
        //    Cuando el dispositivo pase de sin-red → con-red, sincronizará
        //    todas las operaciones que quedaron pendientes (PENDING_CREATE,
        //    PENDING_UPDATE, PENDING_DELETE) y bajará el estado más reciente
        //    del servidor.
        networkObserver = NetworkConnectivityObserver(
            context = applicationContext,
            onNetworkAvailable = {
                if (::vm.isInitialized) {
                    vm.syncPendingOnReconnect()
                }
            }
        )
        networkObserver.register()

        setContent {
            val navController = rememberNavController()
            val vmInstance: MainViewModel = viewModel(
                factory = MainViewModelFactory(applicationContext)
            )
            vm = vmInstance

            Surface(color = MaterialTheme.colorScheme.background) {
                NavGraph(navController = navController, viewModel = vmInstance)
            }
        }

        // Ciclo de vida: polling en tiempo real mientras la app está en foreground.
        // ✅ FIX: se reemplaza el doble polling redundante (startRealtimeSync +
        //         startRealtimeSyncChangedNow) por uno solo basado en firma.
        //         Intervalo: 5s. Solo descarga si hay cambios reales en el servidor.
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                if (::vm.isInitialized) {
                    vm.fetchItems()                     // sync inmediato al volver a pantalla
                    vm.startRealtimeSync(intervalMs = 5_000L)
                }
            }
            override fun onStop(owner: LifecycleOwner) {
                if (::vm.isInitialized) {
                    vm.stopRealtimeSync()
                    vm.autoSaveDraft()                  // guardar borrador al salir
                }
            }
        })

        // WorkManager: sync de fondo cada 15 minutos con red disponible
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncDataWork",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    // onPause NO llama autoSaveDraft: ya lo hace onStop del lifecycle observer.
    // Tenerlo en ambos causaba doble guardado y creación de registros en blanco.

    override fun onDestroy() {
        // ✅ NUEVO: desregistrar el callback de red para evitar memory leaks
        networkObserver.unregister()
        super.onDestroy()
    }
}
