package org.mab.playground

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View


class UpArrow : View {
    private var TAG = UpArrow::class.java.simpleName
    private var arrowPaint: Paint? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {

        Log.d(TAG, " Init called !")
        arrowPaint = Paint()
        arrowPaint!!.color = Color.WHITE
        arrowPaint!!.style = Paint.Style.STROKE
        arrowPaint!!.strokeCap = Paint.Cap.ROUND

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        arrowPaint?.strokeWidth = ((width) * 0.5f) * 0.2f
        canvas.drawLine((width / 2).toFloat(), height * 0.1f, 0f, height.toFloat(), arrowPaint!!)
        canvas.drawLine((width / 2).toFloat(), height * 0.1f, width.toFloat(), height.toFloat(), arrowPaint!!)


    }
}

fun callMe(x: Boolean) {
    when (x) {
        (x == true) -> {
            print("$x==TRUE")
        }
        (x == false) -> {
            print("$x==FALSE")
        }
    }

}

