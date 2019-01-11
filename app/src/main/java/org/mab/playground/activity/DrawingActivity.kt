package org.mab.playground.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_drwaing.*
import org.mab.playground.R

class DrawingActivity : AppCompatActivity() {

    private val TAG = DrawingActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drwaing)
        drawing_view.setOnDragListener { v, event ->
            Log.d(TAG, "Action : ${event.action} ")
            true
        }
        drawing_view.setOnTouchListener { v, event ->
            Log.d(TAG, "Action : ${event.action} ")
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    drawing_view.startDrawing(event.x, event.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    drawing_view.continueDraw(event.x, event.y)
                }
                MotionEvent.ACTION_UP -> {
                    drawing_view.stopDrawing()
                }
                else -> {

                }
            }
            true

        }

        red_color.setOnClickListener {
            drawing_view.setColor(Color.RED)
        }

        green_color.setOnClickListener {
            drawing_view.setColor(Color.GREEN)
        }
        blue_color.setOnClickListener {
            drawing_view.setColor(Color.BLUE)
        }

        undo.setOnClickListener {
            drawing_view.undo()
        }
    }
}

