package com.tuempresa.miapp.data.sync

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Observa cambios de conectividad y ejecuta [onNetworkAvailable] cada vez
 * que el dispositivo recupera acceso a Internet.
 *
 * Uso en MainActivity:
 *   - Registrar en onCreate()
 *   - Desregistrar en onDestroy()
 */
class NetworkConnectivityObserver(
    context: Context,
    private val onNetworkAvailable: suspend () -> Unit
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Scope propio con SupervisorJob para que un fallo no cancele los demás lanzamientos
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            Log.d(TAG, "Red disponible — disparando sync de pendientes")
            scope.launch {
                try {
                    onNetworkAvailable()
                } catch (e: Exception) {
                    Log.e(TAG, "Error al sincronizar tras reconexión: ${e.message}")
                }
            }
        }

        override fun onLost(network: Network) {
            Log.d(TAG, "Red perdida")
        }
    }

    fun register() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        try {
            connectivityManager.registerNetworkCallback(request, networkCallback)
            Log.d(TAG, "NetworkCallback registrado")
        } catch (e: Exception) {
            Log.e(TAG, "Error registrando NetworkCallback: ${e.message}")
        }
    }

    fun unregister() {
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
            Log.d(TAG, "NetworkCallback desregistrado")
        } catch (e: Exception) {
            Log.e(TAG, "Error desregistrando NetworkCallback: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "NetworkObserver"
    }
}
