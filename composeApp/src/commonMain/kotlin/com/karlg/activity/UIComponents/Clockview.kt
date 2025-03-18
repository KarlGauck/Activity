package com.karlg.activity.UIComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material.Text
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karlg.activity.logic.timer.Timer
import com.karlg.activity.logic.timer.Timestamp

@Composable
fun ClockView() {
    var text by remember { mutableStateOf("") }
    var currentProgress by remember { mutableStateOf(0f) }

    val timer by remember { mutableStateOf(
        Timer(120,
            onTick = { ts: Timestamp ->
                text = "${ts.hours} ${ts.minutes} ${ts.seconds}"
                currentProgress = ts.progress
            },
            onFinish = {
                text = "Timer finished"
            })
    ) }
    timer.init()

    Column (modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
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
            CircularProgressIndicator(
                progress =  currentProgress,
                modifier = Modifier.fillMaxWidth()
            )
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