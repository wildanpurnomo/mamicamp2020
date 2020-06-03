package com.wildanpurnomo.timefighter.manager

import android.content.Context
import android.content.SharedPreferences
import com.wildanpurnomo.timefighter.utils.Constants

class SharedPreferencesManager(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(
            Constants.SharedPreferencesKeys.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun saveString(KEY_NAME: String, value: String) {
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun saveInt(KEY_NAME: String, value: Int) {
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    fun saveLong(KEY_NAME: String, value: Long) {
        editor.putLong(KEY_NAME, value)
        editor.apply()
    }

    fun saveBoolean(KEY_NAME: String, value: Boolean) {
        editor.putBoolean(KEY_NAME, value)
        editor.apply()
    }

    fun saveFloat(KEY_NAME: String, value: Float) {
        editor.putFloat(KEY_NAME, value)
        editor.apply()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueInt(KEY_NAME: String): Int? {
        return sharedPref.getInt(KEY_NAME, -1)
    }

    fun getValueLong(KEY_NAME: String): Long? {
        return sharedPref.getLong(KEY_NAME, -1)
    }

    fun getValueBoolean(KEY_NAME: String): Boolean? {
        return sharedPref.getBoolean(KEY_NAME, false)
    }

    fun getValueFloat(KEY_NAME: String): Float? {
        return sharedPref.getFloat(KEY_NAME, -1F)
    }

    fun clearSharedPreference() {
        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {
        editor.remove(KEY_NAME)
        editor.apply()
    }
}