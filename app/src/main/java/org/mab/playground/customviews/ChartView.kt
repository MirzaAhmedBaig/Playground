package org.mab.playground.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import org.mab.playground.R
import org.mab.playground.customviews.helper.DefaultCirclePaint
import org.mab.playground.customviews.helper.DefaultLinePaint
import org.mab.playground.customviews.helper.DefaultTextPaint
import org.mab.playground.extensions.dpToPx


class ChartView : View {

    interface SelectedItemListener {
        fun onItemSelected(resultType: Int, value: Int)
    }

    val BREAHTING = 1
    val RELAXATION = 2
    val MINDFULLNESS = 3
    private val TAG = ChartView::class.java.simpleName

    private var selectedItem = BREAHTING

    constructor(context: Context) : super(context) {
        init()
    }

    private var selectedItemListener: SelectedItemListener? = null
    fun setSelectedItemListener(itemListener: SelectedItemListener) {
        this.selectedItemListener = itemListener
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private var mindfulenssValue = 100
    private var breathingValue = 50
    private var relaxationValue = 60

    fun setValues(mindfulenss: Int, breathing: Int, relaxation: Int) {
        this.mindfulenssValue = mindfulenss
        this.breathingValue = breathing
        this.relaxationValue = relaxation
        invalidate()
    }


    private val leftAlignPaint by DefaultTextPaint {
        textAlign = Paint.Align.LEFT
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, resources.displayMetrics)
    }

    private val rightAlignPaint by DefaultTextPaint {
        textAlign = Paint.Align.RIGHT
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, resources.displayMetrics)
    }

    private val centerAlignPaint by DefaultTextPaint {
        textAlign = Paint.Align.CENTER
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, resources.displayMetrics)
    }

    private val filledCirclePaint by DefaultCirclePaint {
        style = Paint.Style.FILL
    }

    private val strokedCirclePaint by DefaultCirclePaint {
        style = Paint.Style.STROKE
        strokeWidth = 3.dpToPx().toFloat()
    }


    private val highlighterCirclePaint by DefaultCirclePaint {
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 1f.dpToPx()
    }
    private val strokedBlueCirclePaint by DefaultCirclePaint {
        style = Paint.Style.STROKE
        color = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        strokeWidth = 1f.dpToPx()
    }

    private val whiteLinePaint by DefaultLinePaint {
        color = Color.WHITE
        strokeWidth = 1f.dpToPx()
    }

    private val blueLinePaint by DefaultLinePaint {
        color = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        strokeWidth = 1f.dpToPx()
    }

    private val outStrokedTrianglePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 1.dpToPx().toFloat()
        }
    }

    private val inFilledTrianglePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
    }

    private val outStrokedTrianglePath by lazy {
        Path().apply {
            moveTo(topPoint.x, topPoint.y)
            lineTo(leftPoint.x, leftPoint.y)
            lineTo(rightPoint.x, rightPoint.y)
            close()
        }
    }


    private val marginInPx by lazy {
        10.dpToPx()
    }

    private val radius by lazy {
        7.dpToPx()
    }

    private val nineDpInPX by lazy {
        30.dpToPx().toFloat()
    }

    private fun init() {
        setPadding(marginInPx, marginInPx, marginInPx, marginInPx)
    }

    private val heightOfTriangle by lazy {
        height - (2 * nineDpInPX)
    }

    private val sideLengthInPx by lazy {
        (heightOfTriangle * 2) / Math.sqrt(3.0)
    }

    private val topPoint by lazy {
        Point(width / 2.toFloat(), nineDpInPX)
    }
    private val leftPoint by lazy {
        Point(width / 2.toFloat() - (sideLengthInPx / 2).toFloat(), height - 2 * nineDpInPX)
    }
    private val rightPoint by lazy {
        Point(width / 2.toFloat() + (sideLengthInPx / 2).toFloat(), height - 2 * nineDpInPX)
    }
    private val centerPoint by lazy {
        Point(width / 2.toFloat(), height / 2.toFloat() + nineDpInPX)
    }

    private val distanceFromCenterTop by lazy {
        Math.sqrt(Math.pow(((topPoint.x - centerPoint.x).toDouble()), 2.0) + Math.pow((topPoint.y - centerPoint.y).toDouble(), 2.0))
    }
    private val distanceFromCenterLeft by lazy {
        Math.sqrt(Math.pow(((leftPoint.x - centerPoint.x).toDouble()), 2.0) + Math.pow((leftPoint.y - centerPoint.y).toDouble(), 2.0))
    }
    private val distanceFromCenterRight by lazy {
        Math.sqrt(Math.pow(((rightPoint.x - centerPoint.x).toDouble()), 2.0) + Math.pow((rightPoint.y - centerPoint.y).toDouble(), 2.0))
    }

    private fun getCirclePaint(type: Int) =
            if (selectedItem == type) {
                filledCirclePaint
            } else strokedCirclePaint

    override fun onDraw(canvas: Canvas?) {

        canvas?.drawPath(outStrokedTrianglePath, outStrokedTrianglePaint)

        canvas?.drawLine(centerPoint.x, centerPoint.y, topPoint.x, topPoint.y, whiteLinePaint)
        canvas?.drawLine(centerPoint.x, centerPoint.y, leftPoint.x, leftPoint.y, whiteLinePaint)
        canvas?.drawLine(centerPoint.x, centerPoint.y, rightPoint.x, rightPoint.y, whiteLinePaint)

        drawResults(canvas)

        canvas?.drawCircle(centerPoint.x, centerPoint.y, radius.toFloat(), filledCirclePaint)
        canvas?.drawCircle(centerPoint.x, centerPoint.y, radius.toFloat(), strokedBlueCirclePaint)

        canvas?.drawCircle(topPoint.x, topPoint.y, radius.toFloat(), /*getCirclePaint(MINDFULLNESS)*/filledCirclePaint)
        canvas?.drawCircle(leftPoint.x, leftPoint.y, radius.toFloat(), filledCirclePaint)
        canvas?.drawCircle(rightPoint.x, rightPoint.y, radius.toFloat(), filledCirclePaint)

        canvas?.drawCircle(topPoint.x, topPoint.y, radius.toFloat() + 3.dpToPx(), highlighterCirclePaint)
        canvas?.drawCircle(leftPoint.x, leftPoint.y, radius.toFloat() + 3.dpToPx(), highlighterCirclePaint)
        canvas?.drawCircle(rightPoint.x, rightPoint.y, radius.toFloat() + 3.dpToPx(), highlighterCirclePaint)

        canvas?.drawText("$mindfulenssValue %", width / 2.toFloat(), 13.dpToPx().toFloat(), centerAlignPaint)
        canvas?.drawText("$breathingValue %", width / 2.toFloat() - (sideLengthInPx / 2).toFloat() - 15.dpToPx(), height.toFloat() - radius * 4, leftAlignPaint)
        canvas?.drawText("$relaxationValue %", width / 2.toFloat() + (sideLengthInPx / 2).toFloat() + 15.dpToPx(), height.toFloat() - radius * 4, rightAlignPaint)

    }

    private fun drawResults(canvas: Canvas?) {
        Log.d(TAG, "Distances are top:$distanceFromCenterTop left:$distanceFromCenterLeft right:$distanceFromCenterRight")

        Log.d(TAG, "Mindfulenss values : value:$mindfulenssValue distancefrocenter:$distanceFromCenterTop")
        val mD = distanceFromCenterTop * (mindfulenssValue / 100.0f)
        Log.d(TAG, "Mindfulenss values : toppoint:$topPoint centerpoint:$centerPoint")
        val mV = Point(topPoint.x - centerPoint.x, topPoint.y - centerPoint.y)
        Log.d(TAG, "Mindfulenss values :mV:$mV")
        val mV1V2Square = Math.sqrt(Math.pow(mV.x.toDouble(), 2.0) + Math.pow(mV.y.toDouble(), 2.0))
        Log.d(TAG, "Mindfulenss values :mV1V2Square:$mV")
        val mU = Point((mV.x / mV1V2Square).toFloat(), (mV.y / mV1V2Square).toFloat())
        Log.d(TAG, "Mindfulenss values :mV:$mU")
        val mResultPoint = Point((centerPoint.x + (mD * mU.x)).toFloat(), (centerPoint.y + (mD * mU.y)).toFloat())
        Log.d(TAG, "Mindfulenss values :ResultPoint:$mResultPoint")

        val bD = distanceFromCenterLeft * (breathingValue / 100.0f)
        val bV = Point(leftPoint.x - centerPoint.x, leftPoint.y - centerPoint.y)
        val bV1V2Square = Math.sqrt(Math.pow(bV.x.toDouble(), 2.0) + Math.pow(bV.y.toDouble(), 2.0))
        val bU = Point((bV.x / bV1V2Square).toFloat(), (bV.y / bV1V2Square).toFloat())
        val bResultPoint = Point((centerPoint.x + (bD * bU.x)).toFloat(), (centerPoint.y + (bD * bU.y)).toFloat())

        val rD = distanceFromCenterRight * (relaxationValue / 100.0f)
        val rV = Point(rightPoint.x - centerPoint.x, rightPoint.y - centerPoint.y)
        val rV1V2Square = Math.sqrt(Math.pow(rV.x.toDouble(), 2.0) + Math.pow(rV.y.toDouble(), 2.0))
        val rU = Point((rV.x / rV1V2Square).toFloat(), (rV.y / rV1V2Square).toFloat())
        val rResultPoint = Point((centerPoint.x + (rD * rU.x)).toFloat(), (centerPoint.y + (rD * rU.y)).toFloat())

        Log.d(TAG, "Result points Mindfulness : ${mResultPoint.x},${mResultPoint.y}")
        Log.d(TAG, "Result points Breathing : ${bResultPoint.x},${bResultPoint.y}")
        Log.d(TAG, "Result points Relaxation : ${rResultPoint.x},${rResultPoint.y}")


        val resultPath = Path().apply {
            moveTo(mResultPoint.x, mResultPoint.y)
            lineTo(bResultPoint.x, bResultPoint.y)
            lineTo(rResultPoint.x, rResultPoint.y)
            close()
        }
        canvas?.drawPath(resultPath, inFilledTrianglePaint)

        canvas?.drawLine(centerPoint.x, centerPoint.y, mResultPoint.x, mResultPoint.y, blueLinePaint)
        canvas?.drawLine(centerPoint.x, centerPoint.y, bResultPoint.x, bResultPoint.y, blueLinePaint)
        canvas?.drawLine(centerPoint.x, centerPoint.y, rResultPoint.x, rResultPoint.y, blueLinePaint)

    }


    init {
        init()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            touchedAt(event)
        }
        return true
    }


    fun setSelectedItem(resultType: Int, resultValue: Int) {
        selectedItem = resultType
        invalidate()
        selectedItemListener?.onItemSelected(resultType, resultValue)
    }

    private fun touchedAt(event: MotionEvent) {
        Log.d(TAG, "Touched at x:${event.x} y:${event.y}")
        when {
            motionEventInterCepting(event, topPoint) -> {
                setSelectedItem(MINDFULLNESS, mindfulenssValue)
            }
            motionEventInterCepting(event, leftPoint) -> {
                setSelectedItem(BREAHTING, breathingValue)
            }
            motionEventInterCepting(event, rightPoint) -> {
                setSelectedItem(RELAXATION, relaxationValue)
            }
        }
    }


    private fun motionEventInterCepting(touchEvent: MotionEvent, point: Point) =
            Math.abs(touchEvent.x - point.x) <= 10 && Math.abs(touchEvent.y - point.y) <= 10


}

data class Point(val x: Float, val y: Float)
