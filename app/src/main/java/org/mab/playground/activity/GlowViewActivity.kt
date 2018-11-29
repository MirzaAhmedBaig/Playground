package org.mab.playground.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_glow_view.*
import org.mab.playground.R


class GlowViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glow_view)

        animate.setOnClickListener {
            handler.post(runnable)
        }

    }

    var fromAlpha = 1f
    var toAlpha = 0.4f
    private var handler = android.os.Handler()
    private var runnable = object : Runnable {
        override fun run() {
            arrow_one.startAnimation(animationOne)
            arrow_two.startAnimation(animationTwo)
            handler.postDelayed(this, 1000)
        }

    }

    private val animationOne = AlphaAnimation(fromAlpha, toAlpha).apply {
        duration = 500
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                fromAlpha += toAlpha
                toAlpha = fromAlpha - toAlpha
                fromAlpha -= toAlpha
            }

            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {}

        })
    }
    private val animationTwo = AlphaAnimation(fromAlpha, toAlpha).apply {
        duration = 500
        startOffset = 100
    }


}
