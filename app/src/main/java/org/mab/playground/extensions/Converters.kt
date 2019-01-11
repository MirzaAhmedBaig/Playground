package org.mab.playground.extensions

import android.app.Activity
import android.content.res.Resources
import android.view.View

fun Int.dpToPx(): Int {
    return ((this * Resources.getSystem().displayMetrics.density).toInt())
}

fun Float.dpToPx(): Float {
    return ((this * Resources.getSystem().displayMetrics.density))
}

fun Int.pxToDp(): Int {
    return ((this / Resources.getSystem().displayMetrics.density).toInt())
}

fun Float.pxToDp(): Int {
    return ((this / Resources.getSystem().displayMetrics.density).toInt())
}

internal inline fun View.runOnUiThread(crossinline runnable: () -> Unit) {
    (context as Activity).runOnUiThread {
        runnable.invoke()
    }
}