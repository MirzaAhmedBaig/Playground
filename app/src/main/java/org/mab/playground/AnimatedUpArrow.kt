package org.mab.playground

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator


class AnimatedUpArrow : View {
    private var TAG = AnimatedUpArrow::class.java.simpleName
    private var arcPaintOne: Paint? = null
    private var arcPaintTwo: Paint? = null
    private var animHandler = android.os.Handler()
    private val argbEvaluator = ArgbEvaluator()
    private val animatorOne = ValueAnimator.ofObject(argbEvaluator, Color.parseColor("#4EFFFFFF"), Color.WHITE)
    private val animatorTwo = ValueAnimator.ofObject(argbEvaluator, Color.parseColor("#4EFFFFFF"), Color.WHITE)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        /* val a = context.theme.obtainStyledAttributes(
                 attrs,
                 R.styleable.RangeView,
                 0, 0)
         range = a.getInteger(R.styleable.RangeView_range, 3)
         a.recycle()*/
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {

        Log.d(TAG, " Init called !")
        arcPaintOne = Paint()
        arcPaintTwo = Paint()
        arcPaintOne!!.color = Color.WHITE
        arcPaintTwo!!.color = Color.WHITE
        arcPaintOne!!.style = Paint.Style.STROKE
        arcPaintOne!!.strokeCap = Paint.Cap.ROUND

        arcPaintTwo!!.style = Paint.Style.STROKE
        arcPaintTwo!!.strokeCap = Paint.Cap.ROUND

        animatorOne.interpolator = AccelerateDecelerateInterpolator()
        animatorTwo.interpolator = AccelerateDecelerateInterpolator()


        animatorOne.duration = 500
        animatorTwo.duration = 400
        animatorTwo.startDelay = 100

        animatorOne.addUpdateListener { animator ->
            arcPaintOne?.color = animator.animatedValue as Int
            invalidate()
        }
        animatorTwo.addUpdateListener { animator ->
            arcPaintTwo?.color = animator.animatedValue as Int
            invalidate()
        }
    }

    fun startAnimation() {
        animHandler.post(runnable)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        arcPaintOne?.strokeWidth = ((width) * 0.5f) * 0.2f
        arcPaintTwo?.strokeWidth = ((width) * 0.5f) * 0.2f
        val inBetweenOffset = arcPaintOne!!.strokeWidth * 4
        canvas.drawLine(width * 0.5f, height * 0.1f + inBetweenOffset, width * 0.1f, height * 0.9f, arcPaintOne!!)
        canvas.drawLine(width * 0.5f, height * 0.1f + inBetweenOffset, width * 0.9f, height * 0.9f, arcPaintOne!!)

        canvas.drawLine(width * 0.5f, height * 0.1f, width * 0.1f, height * 0.9f - inBetweenOffset, arcPaintTwo!!)
        canvas.drawLine(width * 0.5f, height * 0.1f, width * 0.9f, height * 0.9f - inBetweenOffset, arcPaintTwo!!)


    }

    private val runnable = object : Runnable {
        override fun run() {
            animatorOne.start()
            animatorTwo.start()
            animHandler.postDelayed(this, 2000)
        }

    }

}