package com.example.android.kotlin.appwidgetsample

import android.content.Context
import android.support.v4.content.ContextCompat

class MyPrefs private constructor(context: Context, val appWidgetId: Int) {
    companion object {
        // Name of shared preferences file & key
        private const val SHARED_PREF_FILE = "com.example.android.kotlin.appwidgetsample"
        private const val COUNT_KEY = "count"

        @Volatile
        private var INSTANCE: MyPrefs? = null

        fun getInstance(context: Context, appWidgetId: Int): MyPrefs =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildPrefs(context, appWidgetId).also { INSTANCE = it }
                }

        private fun buildPrefs(context: Context, appWidgetId: Int) = MyPrefs(context, appWidgetId)
    }

    // Shared preferences object
    private val mPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
    var count
        get() = mPreferences.getInt(COUNT_KEY + appWidgetId, 0)
        set(value) = mPreferences.edit().putInt(COUNT_KEY + appWidgetId, value).apply()
}