package com.karlg.activity.logic.timer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

class Timer (
    seconds: Int,
    val onTick: (Timestamp) -> Unit,
    val onFinish: () -> Unit
) {

    private val MAX_MILLISECONDS = seconds * 1000
    private var milliseconds = MAX_MILLISECONDS

    private val stepSizeMillis = 100

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
                delay(stepSizeMillis.toLong())
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
        milliseconds = MAX_MILLISECONDS
    }

    fun step() {

        if (milliseconds == 0) {
            paused = true
            onFinish.invoke()
            return
        }

        milliseconds -= stepSizeMillis

        val displaySeconds = (milliseconds/1000) % 60
        val displayMinutes = (milliseconds / (1000*60)) % 60
        val displayHours = milliseconds / (1000*60*60)

        onTick.invoke(Timestamp(displaySeconds, displayMinutes, displayHours, 1-(milliseconds.toFloat()/MAX_MILLISECONDS)))
    }

}