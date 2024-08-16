package com.shawn.mvvmslideproject.util.sharePreference

import android.content.Context
import android.content.SharedPreferences
import com.shawn.mvvmslideproject.MvvmSlideProjectApplication.Companion.applicationContext
import kotlin.reflect.KProperty

class Preference<T>(val name: String,val key:String, private val default: T) {
    private val prefs: SharedPreferences by lazy { applicationContext().getSharedPreferences(name, Context.MODE_PRIVATE) }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharePreferences(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putSharePrefernces(key, value)
    }

    private fun putSharePrefernces(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("this type of data cannot be saved!")
        }.apply()
    }

    private fun getSharePreferences(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")
        }!!
        return res as T
    }
}