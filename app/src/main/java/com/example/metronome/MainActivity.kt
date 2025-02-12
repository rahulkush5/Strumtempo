package com.example.metronome

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isPlaying = false
    private var bpm = 60
    private lateinit var beatTextView: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val beatRunnable = object : Runnable {
        override fun run() {
            if (isPlaying) {
                updateBeat()
                handler.postDelayed(this, (60000 / bpm).toLong())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beatTextView = findViewById(R.id.beatTextView)
        val startStopButton: Button = findViewById(R.id.startStopButton)

        startStopButton.setOnClickListener {
            isPlaying = !isPlaying
            if (isPlaying) {
                handler.post(beatRunnable)
                startStopButton.text = "Stop"
            } else {
                handler.removeCallbacks(beatRunnable)
                startStopButton.text = "Start"
            }
        }
    }

    private fun updateBeat() {
        val currentBeat = (beatTextView.text.toString().toInt() % 4) + 1
        beatTextView.text = currentBeat.toString()
    }
}
