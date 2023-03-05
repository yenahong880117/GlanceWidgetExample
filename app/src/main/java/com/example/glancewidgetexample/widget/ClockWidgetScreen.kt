package com.example.glancewidgetexample.widget

import androidx.compose.runtime.Composable
import androidx.glance.text.Text

@Composable
fun ClockWidgetScreen(time: String) {
    Text(text = time)
}