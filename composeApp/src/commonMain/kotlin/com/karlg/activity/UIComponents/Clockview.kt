package com.karlg.activity.UIComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material.Text
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karlg.activity.logic.Timer

@Composable
fun ClockView() {
    var text by remember { mutableStateOf("") }

    val timer by remember { mutableStateOf(
        Timer(0, 0, 10,
            onTick = { h: Int, m: Int, s: Int ->
                text = "$h $m $s"
            },
            onFinish = {
                text = "Timer finished"
            })
    ) }
    timer.init()

    Column (modifier = Modifier.padding(20.dp)){
        Text(text)
        Row {
            Button(
                onClick = {
                    if (timer.paused)
                        timer.start()
                    else
                        timer.stop()
                }
            ) {
                Text(if (timer.paused) "Start " else "Stop")
            }
            Button(
                onClick = {
                    timer.reset()
                    timer.stop()
                }
            ) {
                Text("Reset")
            }
        }
    }
}