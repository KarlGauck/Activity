package com.karlg.activity.logic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock

class Timer (
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
    val onTick: (Int, Int, Int) -> Unit,
    val onFinish: () -> Unit
) {

    var currentHours = hours
        private set
    var currentMinutes = minutes
        private set
    var currentSeconds = seconds

    var paused = false
        private set

    // Weather LaunchedEffect is still running
    private var running = false

    @Composable
    fun init() {
        running = true
        LaunchedEffect (key1 = Unit, block = {
            while (running) {
                if (!paused)
                    step()
                delay(1000)
            }
        })
    }

    fun start() {
        paused = false
    }

    fun stop() {
        paused = true
    }

    fun reset() {
        currentSeconds = seconds
        currentMinutes = minutes
        currentHours = hours
    }

    fun step() {

        if (currentSeconds == 0 && currentMinutes == 0 && currentHours == 0) {
            paused = true
            onFinish.invoke()
            return
        }

        onTick.invoke(currentHours, currentMinutes, currentSeconds)

        currentSeconds = (currentSeconds - 1) % 60
        if (currentSeconds != 59)
            return
        currentMinutes = (currentMinutes - 1) % 60
        if (currentMinutes != 59)
            return
        currentHours --
    }

}