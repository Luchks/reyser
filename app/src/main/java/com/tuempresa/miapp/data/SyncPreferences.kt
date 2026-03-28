package com.tuempresa.miapp.data
import android.content.Context
class SyncPreferences(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    var lastSignature: String
        get() = prefs.getString(KEY_SIGNATURE, "") ?: ""
        set(value) = prefs.edit().putString(KEY_SIGNATURE, value).apply()
    fun clear() = prefs.edit().clear().apply()
    companion object {
        private const val PREFS_NAME   = "sync_prefs"
        private const val KEY_SIGNATURE = "last_signature"
    }
}