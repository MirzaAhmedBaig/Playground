package org.mab.playground.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_play_ground.*
import org.mab.playground.R
import java.lang.Math.random


class PlayGroundActivity : AppCompatActivity() {
    private val TAG = PlayGroundActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_ground)

        handler.postDelayed(runnable, 1000)
        wave_view.startUpdate()
        ring_view.post {
            ring_view.startAnimating()
        }
    }

    private var target = 1f
    private var currentAmplitude = 1f
    private val handler = Handler()
    private var targetAmplitude: Double = 0.toDouble()
    private val runnable = object : Runnable {
        override fun run() {
            currentAmplitude = wave_view.getmAmplitude()
            if (targetAmplitude <= 0.0) {
                targetAmplitude = 0.01
            } else if (targetAmplitude >= 20) {
                targetAmplitude = 20.0
            }
            target = (targetAmplitude / 20).toFloat()
            if (target != currentAmplitude) {
                if (target > currentAmplitude) {
                    wave_view.updateAmplitude((currentAmplitude + 0.001).toFloat())
                } else if (target < currentAmplitude) {
                    wave_view.updateAmplitude((currentAmplitude - 0.001).toFloat())
                }
            }
            targetAmplitude = random() * 100
            handler.postDelayed(this, 100)
        }

    }
}

