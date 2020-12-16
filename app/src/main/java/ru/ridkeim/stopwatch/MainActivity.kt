package ru.ridkeim.stopwatch

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ru.ridkeim.stopwatch.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var amBinding: ActivityMainBinding
    private var isTimerWasRunning = false
    private var isTimerRunning = false
    private var seconds = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(amBinding.root)
        amBinding.buttonStart.setOnClickListener {
            isTimerRunning = true
        }
        amBinding.buttonStop.setOnClickListener {
            isTimerRunning = false
        }
        amBinding.buttonReset.setOnClickListener {
            seconds = 0
        }

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isTimerRunning = savedInstanceState.getBoolean("isRunning");
            isTimerWasRunning = savedInstanceState.getBoolean("isWasRunning");
        }
        runTimer()
    }

    private fun runTimer(){
        val textStopWatch = amBinding.textStopWatch
        val handler = Handler()
        
        handler.post(
            object : Runnable {
                override fun run() {
                    val hours: Int = seconds  / 3600
                    val minutes: Int = seconds % 3600 / 60
                    val secs: Int = seconds % 60
                    val time = String.format("%d:%02d:%02d",
                            hours, minutes, secs)
                    textStopWatch.setText(time)
                    if (isTimerRunning) {
                        seconds++
                    }
                    handler.postDelayed(this,1000)
                }
            }
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isTimerRunning);
        outState.putBoolean("isWasRunning", isTimerWasRunning);
    }

    override fun onPause() {
        super.onPause()
        isTimerWasRunning = isTimerRunning
        isTimerRunning = false
    }

    override fun onResume() {
        super.onResume()
        if(isTimerWasRunning){
            isTimerRunning = true
        }
    }
}