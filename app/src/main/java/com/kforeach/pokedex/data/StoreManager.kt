package com.kforeach.pokedex.data

import android.content.Context
import androidx.core.content.edit

class StoreManager(context: Context) {
    private val prefs = context.getSharedPreferences("poc_prefs", Context.MODE_PRIVATE)

    fun isFirstLaunch(): Boolean {
        return prefs.getBoolean(KEY_FIRST_LAUNCH, true)
    }

    fun setFirstLaunchCompleted() {
        prefs.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
    }

    fun resetFirstLaunch() {
        prefs.edit { putBoolean(KEY_FIRST_LAUNCH, true) }
    }

    companion object {
        private const val KEY_FIRST_LAUNCH = "key_first_launch"
    }
}