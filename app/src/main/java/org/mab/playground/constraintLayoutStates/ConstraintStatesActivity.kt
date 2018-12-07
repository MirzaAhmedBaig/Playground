package org.mab.playground.constraintLayoutStates

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_constraint_states.*
import org.mab.playground.R

class ConstraintStatesActivity : AppCompatActivity() {

    private val constraintSet1 by lazy {
        ConstraintSet().apply {
            clone(constraintLayout)
        }
    }
    val constraintSet2 by lazy {
        ConstraintSet()
    }
    private var toggle = false


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_states)




        imageView2.setOnClickListener {
            constraintSet2.clone(this@ConstraintStatesActivity, R.layout.state_end)
            TransitionManager.beginDelayedTransition(constraintLayout)
            constraintSet2.applyTo(constraintLayout)


            if (toggle) {
                constraintSet2.clone(this@ConstraintStatesActivity, R.layout.activity_constraint_states)
                TransitionManager.beginDelayedTransition(constraintLayout)
                constraintSet2.applyTo(constraintLayout)
            } else {
                constraintSet2.clone(this@ConstraintStatesActivity, R.layout.state_end)
                TransitionManager.beginDelayedTransition(constraintLayout)
                constraintSet2.applyTo(constraintLayout)
            }
            toggle = !toggle
        }
    }


}
