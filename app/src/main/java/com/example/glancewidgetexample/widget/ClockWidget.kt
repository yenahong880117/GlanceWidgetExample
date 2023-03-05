package com.example.glancewidgetexample.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.background
import androidx.glance.layout.fillMaxSize
import java.time.LocalDateTime

/**
 * 處理UI
 */
class ClockWidget : GlanceAppWidget() {
    @Composable
    override fun Content() {
        val time = LocalDateTime.now()
        ClockWidgetScreen(
            String.format("%02d:%02d", time.hour, time.minute),
            modifier = GlanceModifier.fillMaxSize().background(
                Color.White
            )
        )
    }
}