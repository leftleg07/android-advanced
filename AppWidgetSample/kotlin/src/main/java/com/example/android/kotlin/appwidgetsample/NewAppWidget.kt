package com.example.android.kotlin.appwidgetsample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.DateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {


    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                        appWidgetId: Int) {

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)
        val prefs = MyPrefs.getInstance(context, appWidgetId)
        val count = prefs.count + 1

        // Get the current time.
        val dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())

        views.setTextViewText(R.id.appwidget_id,
                appWidgetId.toString())
        views.setTextViewText(R.id.appwidget_update,
                context.resources.getString(
                        R.string.date_count_format, count, dateString))
        prefs.count = count

        // Setup update button to send an update request as a pending intent.
        val intentUpdate = Intent(context, NewAppWidget::class.java)

        // The intent action must be an app widget update.
        intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

        // Include the widget ID to be updated as an intent extra.
        val idArray = intArrayOf(appWidgetId)
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)

        // Wrap it all in a pending intent to send a broadcast.
        // Use the app widget ID as the request code (third argument) so that
        // each intent is unique.
        val pendingUpdate = PendingIntent.getBroadcast(context,
                appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT)

        // Assign the pending intent to the button onClick handler
        views.setOnClickPendingIntent(R.id.button_update, pendingUpdate)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}

