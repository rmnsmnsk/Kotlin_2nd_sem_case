package util

import javax.swing.Timer
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class GameTimer {
    private var seconds: Long = 0
    private var running = false
    private var listener: ((Long) -> Unit)? = null

    private val swingTimer = Timer(1000, object : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            if (running) {
                seconds++
                listener?.invoke(seconds)
            }
        }
    })

    fun setOnTick(listener: (Long) -> Unit) {
        this.listener = listener
    }

    fun start() {
        if (!running) {
            running = true
            swingTimer.start()
        }
    }

    fun stop() {
        if (running) {
            running = false
            swingTimer.stop()
        }
    }

    fun reset() {
        seconds = 0
        listener?.invoke(seconds)
    }

    fun getElapsedSeconds(): Long = seconds

    fun getFormattedTime(): String {
        val mins = seconds / 60
        val secs = seconds % 60
        return "%02d:%02d".format(mins, secs)
    }
}