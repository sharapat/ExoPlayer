package uz.usoft.test

import android.content.Context

class Settings(context: Context) {
    private val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun setPlaybackPosition(source: String, position: Long) {
        prefs.edit().putLong(source, position).apply()
    }

    fun getPlaybackPosition(source: String) = prefs.getLong(source, 0)
}