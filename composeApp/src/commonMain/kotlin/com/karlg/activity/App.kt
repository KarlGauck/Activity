package com.karlg.activity

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.karlg.activity.UIComponents.ClockView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        ClockView()
    }
}


