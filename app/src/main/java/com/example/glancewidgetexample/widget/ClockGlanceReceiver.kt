package com.example.glancewidgetexample.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import java.util.Calendar
import androidx.core.content.getSystemService
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.*

class ClockGlanceReceiver : GlanceAppWidgetReceiver() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    override val glanceAppWidget: GlanceAppWidget
        get() = ClockWidget()

    private var isDisable = false

    /**
     * 放widget到桌面時會觸發
     */
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        scheduleUpdate(context)
    }

    /**
     * 移除widget時會觸發
     */
    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        isDisable = true
        context.getSystemService<AlarmManager>()?.cancel(createBroadcastPendingIntent(context))
    }

    /**
     * 狀態改變時會觸發ex:新增 刪除 刷新 widget
     */
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (!isDisable) {
            scope.launch {
                ClockWidget().updateAll(context)
            }
            scheduleUpdate(context)
        }
    }

    /**
     * 計畫刷新
     */
    private fun scheduleUpdate(context: Context) {
        val pendingIntent = createBroadcastPendingIntent(context)
        val calendar = (Calendar.getInstance().clone() as Calendar).apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.MINUTE, 1)
        }

        val alarmManager = context.getSystemService<AlarmManager>()
        alarmManager?.setExact(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
    }

    private fun createBroadcastPendingIntent(context: Context): PendingIntent? {
        val intent = Intent(
            context,
            ClockGlanceReceiver::class.java
        ).setAction("${context.packageName}.tick")
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}