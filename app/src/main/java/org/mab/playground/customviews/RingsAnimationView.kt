package org.mab.playground.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import java.lang.Math.random


/**
 * Created by Mirza Ahmed Baig on 04/01/19.
 * Avantari Technologies
 * mirza@avantari.org
 */
class RingsAnimationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {

    private var radiousOne = 0f
    private var radiousTwo = 0f
    private var radiousThree = 0f
    private var offset = 0f
    private var pointsArray = arrayOf(Pair(0f, 0f), Pair(0f, 0f), Pair(0f, 0f))
    private val circlePaint by lazy {
        Paint().apply {
            color = Color.parseColor("#55ffffff")
            strokeWidth = 5f
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    private fun init() {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        /*offset = width * 0.005f

        radiousOne += offset
        radiousTwo += offset * 0.8f
        radiousThree += offset * 0.6f

        if (radiousOne >= width * 0.8f)
            radiousOne = 0f

        if (radiousTwo >= width * 0.8f)
            radiousTwo = 0f

        if (radiousThree >= width * 0.8f)
            radiousThree = 0f

        canvas?.drawCircle(width * 0.5f, height * 0.5f, radiousOne, circlePaint)
        canvas?.drawCircle(width * 0.5f, height * 0.5f, radiousTwo, circlePaint)
        canvas?.drawCircle(width * 0.5f, height * 0.5f, radiousThree, circlePaint)*/

        offset = width * 0.002f
        radiousOne += offset

        canvas?.drawCircle(pointsArray[0].first, pointsArray[0].second, radiousOne, circlePaint)
        canvas?.drawCircle(pointsArray[1].first, pointsArray[1].second, radiousOne, circlePaint)
        canvas?.drawCircle(pointsArray[2].first, pointsArray[2].second, radiousOne, circlePaint)


    }

    fun startAnimating() {
        animHandler.post(animRunnable)
        valueHandler.post(valueRunnable)
    }

    private val valueHandler = Handler()
    private val valueRunnable = object : Runnable {
        override fun run() {
            invalidate()
            handler.postDelayed(this, 1)
        }

    }

    private val animHandler = Handler()
    private val animRunnable = object : Runnable {
        override fun run() {
            radiousOne = 0f
            pointsArray = arrayOf(Pair((random() * width).toFloat(), (random() * height).toFloat()), Pair((random() * width).toFloat(), (random() * height).toFloat()), Pair((random() * width).toFloat(), (random() * height).toFloat()))
            invalidate()
            handler.postDelayed(this, 1000)
        }

    }

}