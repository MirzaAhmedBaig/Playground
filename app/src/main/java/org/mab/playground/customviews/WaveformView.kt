package org.mab.playground.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.view.View
import org.mab.playground.extensions.runOnUiThread


/**
 * Created by Mirza Ahmed Baig on 04/01/19.
 * Avantari Technologies
 * mirza@avantari.org
 */

class WaveformView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {
    private val mPrimaryWidth = 7f
    private val mSecondaryWidth = 5.5f
    private var mAmplitude = MIN_AMPLITUDE
    var waveColor = Color.WHITE/*parseColor("#f5007e")*/
    private val mDensity = 6
    private val mWaveCount = 10  //Display number of waves
    private val mFrequency = 0.1f
    private val mPhaseShift = -0.1075f
    private var mPhase = mPhaseShift
    private var mPrimaryPaint: Paint? = null
    private var mSecondaryPaint: Paint? = null
    private var mPath: Path? = null
    private val mLastX: Float = 0.toFloat()
    private val mLastY: Float = 0.toFloat()

    lateinit var waveHandlerThread: HandlerThread
    lateinit var waveHandler: Handler

    private val TAG = WaveformView::class.java.simpleName

    init {
        initialize()
    }

    fun getmAmplitude(): Float {
        return mAmplitude
    }

    private fun initialize() {
//        Log.d(TAG, " initialize ${System.currentTimeMillis()}")
        //Displaying the bold lines
        mPrimaryPaint = Paint()
        mPrimaryPaint!!.style = Paint.Style.STROKE
        mPrimaryPaint!!.strokeWidth = mPrimaryWidth
        mPrimaryPaint!!.isAntiAlias = true
        mPrimaryPaint!!.style = Paint.Style.STROKE
        mPrimaryPaint!!.color = waveColor
        //Displaying the thin lines
        mSecondaryPaint = Paint()
        mSecondaryPaint!!.strokeWidth = mSecondaryWidth
        mSecondaryPaint!!.isAntiAlias = true
        mSecondaryPaint!!.style = Paint.Style.STROKE
        mSecondaryPaint!!.color = waveColor

        mPath = Path()


    }

    fun startUpdate() {
        initialize()
        waveHandlerThread = HandlerThread("WaveFormViewHandlerThread")
        waveHandlerThread.start()
        waveHandler = Handler(waveHandlerThread.looper)
        waveHandler.post(runnable)
    }

    private fun updateInvalidate(delay: Long) {
        with(waveHandler) {
            removeCallbacks(runnable)
            if (!paused) {
                postDelayed(runnable, 13)
            }
        }
    }

    var paused: Boolean = false
    fun pauseUpdate() {
        paused = true
    }

    fun resumeUpdate() {
        paused = false
        initialize()
        updateInvalidate(0)
    }

    private var runnable: Runnable = Runnable {
        runOnUiThread {
            invalidate()
            updateInvalidate(13)
        }
    }

    internal fun updateAmplitude(amplitude: Float) {
        var amplitude = amplitude
        if (amplitude == mAmplitude || java.lang.Float.isNaN(amplitude)) {
            return
        }

        //        //Log.d(TAG, "updateAmplitude: "+amplitude);
        if (amplitude > MAX_AMPLITUDE) {
            amplitude = MAX_AMPLITUDE
        } else if (amplitude < MIN_AMPLITUDE) {
            amplitude = MIN_AMPLITUDE
        }

        mAmplitude = amplitude
        //   invalidate();
    }

    override fun onDraw(canvas: Canvas) {
        //    //Log.d(TAG, " onDraw start " + System.currentTimeMillis())
        val width = width        //get height of canvas
        val height = height       //get width of canvas
        val midH = height / 2.0f
        val midW = width / 2.0f

        val maxAmplitude = midH / 2f - 4.0f

        for (l in 0 until mWaveCount) {

            val progress = 1.0f - l * 1.0f / mWaveCount
            val normalAmplitude = (1.5f * progress - 0.5f) * mAmplitude

            val multiplier = Math.min(1.0, (progress / 3.0f * 2.0f + 1.0f / 3.0f).toDouble()).toFloat()

            if (l != 0) {
                mSecondaryPaint!!.alpha = (multiplier * 255).toInt()
            }

            mPath!!.reset()
            var x = 0
            while (x < width + mDensity) {
                val scaling = 1f - Math.pow((1 / midW * (x - midW)).toDouble(), 2.0).toFloat()
                val y = scaling * maxAmplitude * normalAmplitude * Math.sin(
                        180f * x.toFloat() * mFrequency / (width * Math.PI) + mPhase).toFloat() + midH
                //canvas.drawPoint(x, y, l == 0 ? mPrimaryPaint : mSecondaryPaint);

                //canvas.drawLine(x, y, x, 2*midH - y, mSecondaryPaint);
                if (x == 0) {
                    mPath!!.moveTo(x.toFloat(), y)         //Shoes the direction to move
                } else {
                    mPath!!.lineTo(x.toFloat(), y)         //draw the line
                }
                x += mDensity
            }
            //for BOLD Waves
            if (l == 0 || l == 1 || l == 2) {
                canvas.drawPath(mPath!!, mPrimaryPaint!!)
            }
            /* if (l == 1) {
                canvas.drawPath(mPath, mPrimaryPaint);
            }
            if (l == 2) {
                canvas.drawPath(mPath, mPrimaryPaint);
            }*/
            //for THIN waves
            if (l == 7 || l == 8 || l == 9) {
                canvas.drawPath(mPath!!, mSecondaryPaint!!)
            }
            /* if (l == 8) {
                canvas.drawPath(mPath, mSecondaryPaint);
            }
            if (l == 9) {
                canvas.drawPath(mPath, mSecondaryPaint);
            }*/
        }

        mPhase += mPhaseShift
        //Log.d(TAG, " onDraw end " + System.currentTimeMillis())
        // invalidate()
    }

    companion object {
        val MIN_AMPLITUDE = 0.00f
        val MAX_AMPLITUDE = 0.8f
    }
}