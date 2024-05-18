package com.example.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.os.TokenWatcher
import android.widget.Button
import android.widget.Chronometer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var stopWatcher: Chronometer
    private var running = false
    private var offset: Long =0
    val OFFSET_KEY = "offset"
    val RUNNIN_KEY = "running"
    val BASE_KEY = "base"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stopWatcher = findViewById(R.id.stopwatch)

        if(savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNIN_KEY)
            if(running){
                stopWatcher.base = savedInstanceState.getLong(BASE_KEY)
                stopWatcher.start()
            }else setBaseTime()
        }

        val startButton = findViewById<Button>(R.id.bStart)
        startButton.setOnClickListener {
            if(!running){
                setBaseTime()
                stopWatcher.start()
                running=true
            }
        }

        val pauseButton = findViewById<Button>(R.id.bPause)
        pauseButton.setOnClickListener {
            if(running){
                saveOffset()
                stopWatcher.stop()
                running=false
            }
        }

        val resetButton = findViewById<Button>(R.id.bReset)
        resetButton.setOnClickListener {
            offset=0
            setBaseTime()
        }
    }

    fun saveOffset(){
        offset=SystemClock.elapsedRealtime()-stopWatcher.base
    }
    fun setBaseTime(){
        stopWatcher.base = SystemClock.elapsedRealtime()-offset
    }

    override fun onSaveInstanceState(saveInstanceState: Bundle){
        saveInstanceState.putLong(OFFSET_KEY, offset)
        saveInstanceState.putBoolean(RUNNIN_KEY, running)
        saveInstanceState.putLong(BASE_KEY,stopWatcher.base)
        super.onSaveInstanceState(saveInstanceState)
    }

    override fun onStop() {
        super.onStop()
        if(running){
            saveOffset()
            stopWatcher.stop()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if(running){
            setBaseTime()
            stopWatcher.start()
            offset=0
    }
    }


}