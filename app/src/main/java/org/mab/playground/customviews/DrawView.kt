package org.mab.playground.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * Created by Mirza Ahmed Baig on 10/01/19.
 * Avantari Technologies
 * mirza@avantari.org
 */

class DrawingView : View {
    private var selectedColor = Color.BLACK
    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    val colors = arrayOf(Color.BLACK, Color.RED, Color.GREEN, Color.BLUE)

    private val pathList = HashMap<Path, Int>()

    private val path = Path().apply {

    }
    private var undo = false
    private val TAG = DrawingView::class.java.simpleName

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }


    fun startDrawing(x: Float, y: Float) {
        path.reset()
        path.moveTo(x, y)
    }

    fun continueDraw(x: Float, y: Float) {
        path.lineTo(x, y)
        invalidate()
    }

    fun stopDrawing() {
        Log.d(TAG, "Size ${pathList.size}")
        pathList.put(Path(path), selectedColor)
    }

    fun setColor(color: Int) {
        selectedColor = color
    }

    fun undo() {
        undo = true
        pathList.remove(path)
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        Log.d(TAG, "OnDraw")
        super.onDraw(canvas)
        pathList.forEach {
            paint.color = it.value
            canvas?.drawPath(it.key, paint)
        }
        if (!undo) {
            undo = false
            paint.color = selectedColor
            canvas?.drawPath(path, paint)
        }
    }
}