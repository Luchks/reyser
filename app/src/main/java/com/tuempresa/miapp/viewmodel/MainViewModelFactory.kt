package com.tuempresa.miapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuempresa.miapp.data.Repository
import com.tuempresa.miapp.data.RetrofitInstance
import com.tuempresa.miapp.data.SyncPreferences
import com.tuempresa.miapp.data.local.AppDatabase

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val db        = AppDatabase.getInstance(context)
            // ✅ FIX: SyncPreferences inyectado para que lastSignature sobreviva
            //         a rotaciones y recreaciones del ViewModel.
            val syncPrefs = SyncPreferences(context)
            val repo      = Repository(
                api       = RetrofitInstance.api,
                db        = db,
                syncPrefs = syncPrefs
            )
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
