package org.mab.playground.customviews.helper

import android.graphics.Color
import android.graphics.Paint
import org.mab.playground.customviews.ChartView
import org.mab.playground.extensions.dpToPx
import kotlin.reflect.KProperty

class DefaultTextPaint(val function: Paint.() -> Unit) {
    operator fun getValue(chartView: ChartView, property: KProperty<*>): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = 16f
        }
        return paint.also {
            function.invoke(it)
        }
    }
}

class DefaultCirclePaint(val function: Paint.() -> Unit) {
    operator fun getValue(chartView: ChartView, property: KProperty<*>): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
        }
        return paint.also {
            function.invoke(it)
        }
    }
}

class DefaultLinePaint(val function: Paint.() -> Unit) {
    operator fun getValue(chartView: ChartView, property: KProperty<*>): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = 1.dpToPx().toFloat()
        }
        return paint.also {
            function.invoke(it)
        }
    }
}