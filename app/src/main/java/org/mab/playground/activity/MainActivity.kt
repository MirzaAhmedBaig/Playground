package org.mab.playground.activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.Scroller
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.mab.playground.R


class MainActivity : AppCompatActivity() {

    private var ROTAION_ANGLE_OFFSET: Float = 0f
    private var currentPosotion = 0
    private val itemList by lazy {
        ArrayList<String>().apply {
            (1..12).forEach {
                this@apply.add(it.toString())
            }
        }
    }

    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListener()
        setGestures()

    }

    private fun setListener() {

        minutes_wheel.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (minutes_wheel.measuredHeight > 1) {
                    minutes_wheel.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val param = minutes_wheel.layoutParams
                    param.height = (minutes_wheel.measuredHeight - minutes_wheel.measuredHeight * 0.2).toInt()
                    param.width = (minutes_wheel.measuredHeight - minutes_wheel.measuredHeight * 0.2).toInt()
                    minutes_wheel.layoutParams = param
                    setWheel()
                }
            }
        })


        next.setOnClickListener {
            setCurrentPosition(pre.text.toString().toInt())
        }
    }


    private fun setWheel() {
        Log.d(TAG, "call to setWheel")
        val param = dummy_view_one.layoutParams as ConstraintLayout.LayoutParams
        param.width = minutes_wheel.measuredHeight
        param.height = minutes_wheel.measuredHeight
        dummy_view_one.layoutParams = param

        ROTAION_ANGLE_OFFSET = 360.0f / itemList.size
        itemList.forEachIndexed { index, value ->
            val textView = TextView(this@MainActivity).apply {

                id = View.generateViewId()


                text = value.toString()
                textSize = 20f
                setTextColor(Color.WHITE)
                layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    circleConstraint = R.id.dummy_view_one
                    circleRadius = ((dummy_view_one.measuredWidth / 2))
                    circleAngle = ((ROTAION_ANGLE_OFFSET * index) + 90f % 360f)
                }
            }
            minutes_wheel.addView(textView)
        }

    }

    private fun setGestures() {
        //we shoud implement gesture detector, otherwise we cannot get the user input.
        //we have created  GestureListener inner class.
        mDetector = GestureDetector(this, GestureListener())

        //disable the long press gesture.
        mDetector!!.setIsLongpressEnabled(true)


        mPieRotation = 0f

        // Create a Scroller to handle the fling gesture.
        mScroller = Scroller(this)


        mScrollAnimator = ValueAnimator.ofFloat(0f, 1f)
        mScrollAnimator!!.addUpdateListener { tickScrollAnimation() }

        /*minutes_wheel.setOnTouchListener { v, event ->
            var result = mDetector!!.onTouchEvent(event)

            if (!result) {
                if (event!!.action == MotionEvent.ACTION_UP) {
                    stopScrolling()
                    result = true
                }
            }
            result
        }*/

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = mDetector!!.onTouchEvent(event)

        if (!result) {
            if (event!!.action == MotionEvent.ACTION_UP) {
                stopScrolling()
                result = true
            }
        }
        return result
    }


    private var mDetector: GestureDetector? = null

    private var mPieRotation: Float = 0.0f


    var mScroller: Scroller? = null

    private var mScrollAnimator: ValueAnimator? = null

    /**
     * The initial fling velocity is divided by this amount.
     */
    private val FLING_VELOCITY_DOWNSCALE = 6


    /**
     * Extends [GestureDetector.SimpleOnGestureListener] to provide custom gesture
     * processing.
     */
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        //when you try to rotate the image. onscroll will be called behind the scene.
        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            // Set the rotation directly.
            val scrolltorotte = vectorToScalarScroll(
                    distanceX,
                    distanceY,
                    e2.x - (minutes_wheel.width / 2 + minutes_wheel.left),
                    e2.y - (minutes_wheel.height / 2 + minutes_wheel.top))

            setPieRotation(getPieRotation().toFloat() - scrolltorotte / FLING_VELOCITY_DOWNSCALE)
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {

            val scrolltorotte = vectorToScalarScroll(
                    velocityX,
                    velocityY,
                    e2.x - (minutes_wheel.width / 2 + minutes_wheel.left),
                    e2.y - (minutes_wheel.height / 2 + minutes_wheel.top))

            mScroller?.fling(
                    0,
                    getPieRotation().toInt(),
                    0,
                    scrolltorotte.toInt() / FLING_VELOCITY_DOWNSCALE,
                    0,
                    0,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE)

            mScrollAnimator!!.duration = mScroller!!.duration.toLong()
            mScrollAnimator!!.start()
            return true
        }

        override fun onDown(e: MotionEvent): Boolean {
            if (isAnimationRunning()) {
                stopScrolling()
            }
            return true
        }
    }


    private fun isAnimationRunning(): Boolean {
        return !mScroller!!.isFinished
    }

    private fun vectorToScalarScroll(dx: Float, dy: Float, x: Float, y: Float): Float {
        // get the length of the vector
        val l = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()

        // decide if the scalar should be negative or positive by finding
        // the dot product of the vector perpendicular to (x,y).
        val crossX = -y

        val dot = crossX * dx + x * dy
        val sign = Math.signum(dot)

        return l * sign
    }


    fun setPieRotation(rotation: Float) {
        var rotation = rotation
        rotation = (rotation % 360 + 360) % 360
        mPieRotation = rotation
        minutes_wheel.rotation = rotation
        (0 until minutes_wheel.childCount).forEach {
            minutes_wheel.getChildAt(it).rotation = 360 - rotation
        }
    }


    fun getPieRotation(): Float {
        return mPieRotation
    }

    private fun tickScrollAnimation() {
        if (!mScroller!!.isFinished) {
            mScroller!!.computeScrollOffset()
            setPieRotation(mScroller!!.currY.toFloat())
        } else {
            mScrollAnimator!!.cancel()
            onScrollFinished()
        }
    }

    private fun stopScrolling() {
        mScroller!!.forceFinished(true)
        onScrollFinished()
    }


    /**
     * Called when the user finishes a scroll action.
     */
    private fun onScrollFinished() {
        val oldRotation = mPieRotation
        val reminder = mPieRotation.toInt() % ROTAION_ANGLE_OFFSET.toInt()
        if (reminder != 0) {
            mPieRotation = if ((reminder.toFloat() + (mPieRotation - mPieRotation.toInt())) < (ROTAION_ANGLE_OFFSET / 2)) {
                //go down
                ROTAION_ANGLE_OFFSET * (mPieRotation.toInt() / ROTAION_ANGLE_OFFSET.toInt())
            } else {
                ROTAION_ANGLE_OFFSET * ((mPieRotation.toInt() / ROTAION_ANGLE_OFFSET.toInt()) + 1)
            }
        }
        minutes_wheel.rotation = mPieRotation
        ObjectAnimator.ofFloat(minutes_wheel, "rotation", oldRotation, mPieRotation).apply {
            duration = 200
            interpolator = AccelerateInterpolator()

        }.start()
        (0 until minutes_wheel.childCount).forEach {
            minutes_wheel.getChildAt(it).rotation = 360 - mPieRotation
        }
        currentPosotion = (((360 - mPieRotation) / ROTAION_ANGLE_OFFSET) % itemList.size).toInt()
        Log.d(TAG, "Angle Value : ${((360 - mPieRotation) / ROTAION_ANGLE_OFFSET) % itemList.size}")
    }

    fun getCurrentPosition(): Int {
        return (((360 - mPieRotation) / ROTAION_ANGLE_OFFSET) % itemList.size).toInt()
    }

    fun getCurrentItem(): String {
        return itemList[currentPosotion]
    }

    fun setCurrentPosition(index: Int) {
        if (index == currentPosotion)
            return
        val oldRotation = mPieRotation
        Log.d(TAG, "Current Position : $currentPosotion")
        Log.d(TAG, "Current Angle : $currentPosotion")
        if (index > itemList.lastIndex)
            throw IndexOutOfBoundsException()
        mPieRotation = 360 - (index * ROTAION_ANGLE_OFFSET)

        ObjectAnimator.ofFloat(minutes_wheel, "rotation", oldRotation, mPieRotation).apply {
            duration = 200
            interpolator = AccelerateInterpolator()

        }.start()
        (0 until minutes_wheel.childCount).forEach {
            if (minutes_wheel.getChildAt(it) is TextView) {
                ObjectAnimator.ofFloat(minutes_wheel.getChildAt(it), "rotation", 360 - mPieRotation).apply {
                    duration = 200
                    interpolator = AccelerateInterpolator()

                }.start()
            }
        }
        currentPosotion = index
    }
}

