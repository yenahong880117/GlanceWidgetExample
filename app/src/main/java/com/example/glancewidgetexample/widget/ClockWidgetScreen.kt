package com.example.glancewidgetexample.widget

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.text.Text

@Composable
fun ClockWidgetScreen(time: String, modifier: GlanceModifier) {
    Row(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(text = time)
    }

}