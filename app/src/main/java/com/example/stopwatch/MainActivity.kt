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
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //lateinit var stopWatcher: Chronometer
    private var running = false
    private var offset: Long = 0
    val OFFSET_KEY = "offset"
    val RUNNIN_KEY = "running"
    val BASE_KEY = "base"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNIN_KEY)
            if (running) {
                binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                binding.stopwatch.start()
            } else setBaseTime()
        }


        binding.bStart.setOnClickListener {
            if (!running) {
                setBaseTime()
                binding.stopwatch.start()
                running = true
            }
        }

        binding.bPause.setOnClickListener {
            if (running) {
                saveOffset()
                binding.stopwatch.stop()
                running = false
            }
        }

        binding.stopwatch.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
    }

    fun setBaseTime() {
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    override fun onSaveInstanceState(saveInstanceState: Bundle) {
        saveInstanceState.putLong(OFFSET_KEY, offset)
        saveInstanceState.putBoolean(RUNNIN_KEY, running)
        saveInstanceState.putLong(BASE_KEY, binding.stopwatch.base)
        super.onSaveInstanceState(saveInstanceState)
    }

    override fun onStop() {
        super.onStop()
        if (running) {
            saveOffset()
            binding.stopwatch.stop()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (running) {
            setBaseTime()
            binding.stopwatch.start()
            offset = 0
        }
    }


}