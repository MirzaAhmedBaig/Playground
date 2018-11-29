package org.mab.playground.activity

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_play_ground.*
import org.mab.playground.R
import kotlin.math.min
import kotlin.math.sqrt


class PlayGroundActivity : AppCompatActivity() {
    private val TAG = PlayGroundActivity::class.java.simpleName
    private var DEFAULT_IN_BETWEEN_SPACE = 0
    var height = 0
    var width = 0
    private val list by lazy {
        ArrayList<String>().apply {
            (0..59).forEach {
                add((it).toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_ground)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels
        circle.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (circle.measuredWidth > 1) {
                    circle.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val params = circle.layoutParams as ConstraintLayout.LayoutParams
                    /*params.width = (circle.measuredHeight * 0.9).toInt()
                    params.height = (circle.measuredHeight * 0.9).toInt()*/
                    params.height = (circle.measuredWidth)
                    params.width = (circle.measuredWidth)
                    circle.layoutParams = params

                    DEFAULT_IN_BETWEEN_SPACE = (params.width * 0.2f).toInt()

                    val maxCount: Int = min(((params.width / getBiggestTextSize()) * 4).toInt(), list.size)
                    val circleRadius1 = ((((params.width) / 2) * 0.8f).toInt())
                    val ROTAION_ANGLE_OFFSET = 360f / maxCount
                    var multiplier = 0
                    Log.d(TAG, "Max Ites Are : $maxCount")
                    (0 until maxCount / 2).forEach {
                        Log.d(TAG, "Index : $it")
                        val textView = TextView(this@PlayGroundActivity).apply {
                            id = View.generateViewId()
                            text = list[it]
                            textSize = 20f
                            setTextColor(Color.GREEN)
                            layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                                circleConstraint = circle.id
                                circleRadius = circleRadius1
                                circleAngle = (((ROTAION_ANGLE_OFFSET * multiplier) + 90f) % 360f)
                                rotation = circleAngle
                            }
                        }
                        main_view.addView(textView)
                        multiplier++
                    }

                    (list.size - (maxCount / 2) until list.size).forEach {
                        val textView = TextView(this@PlayGroundActivity).apply {
                            Log.d(TAG, "Index : $it")
                            id = View.generateViewId()
                            text = list[it]
                            textSize = 20f
                            setTextColor(Color.GREEN)
                            layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                                circleConstraint = circle.id
                                circleRadius = circleRadius1
                                circleAngle = (((ROTAION_ANGLE_OFFSET * multiplier) + 90f) % 360f)
                                rotation = circleAngle
                            }
                        }
                        main_view.addView(textView)
                        multiplier++
                    }
                }
            }

        })
    }

    private fun getBiggestTextSize(): Float {
        val size = getTextViewSize(this, getBiggestElement(list), 20f, width, null)
        return sqrt((size.first * size.first + size.second * size.second).toFloat()) * 1.7f + DEFAULT_IN_BETWEEN_SPACE
    }

    private fun getTextViewSize(context: Context, text: CharSequence, textSize: Float, deviceWidth: Int, typeface: Typeface?): Pair<Int, Int> {
        val textView = TextView(context)
        textView.typeface = typeface
        textView.setText(text, TextView.BufferType.SPANNABLE)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        textView.measure(widthMeasureSpec, heightMeasureSpec)
        return Pair(textView.measuredWidth, textView.measuredHeight)
    }

    private fun getBiggestElement(arrayList: ArrayList<String>): String {
        var bigSting = arrayList[0]
        (0 until arrayList.size).forEach {
            if (bigSting.length < arrayList[it].length)
                bigSting = arrayList[it]
        }
        return bigSting
    }

    fun dpToPx(dp: Int): Int {
        return ((dp * Resources.getSystem().displayMetrics.density).toInt())
    }


}

