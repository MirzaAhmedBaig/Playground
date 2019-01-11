package org.mab.playground.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pager.*
import org.mab.playground.R
import org.mab.playground.adapters.PagerAdapter
import org.mab.playground.fragments.PageOneFragment
import org.mab.playground.fragments.PageThreeFragment
import org.mab.playground.fragments.PageTwoFragment
import org.mab.playground.fragments.SearchFragment
import org.mab.playground.listeners.PagerOperationListener
import org.mab.playground.preferences.AppPreferences

class PagerActivity : AppCompatActivity(), PagerOperationListener, StepCounterListener {
    private val pagerAdapter by lazy {
        main_pager.adapter as PagerAdapter
    }
    private val appPreference by lazy {
        AppPreferences(this)
    }

    private var serviceIntent: Intent? = null
    private val pageTwo by lazy { PageTwoFragment() }

    override fun onRightSwipeRequest(tag: String) {
        if (appPreference.getModeVisitTrack().contains(tag)) {
            if (pagerAdapter.count == 3) {
                pagerAdapter.removeAt(1)
            }
        } else {
            if (pagerAdapter.count < 3) {
                pagerAdapter.addAt(1, pageTwo)
            }
        }

        main_pager.currentItem = main_pager.currentItem + 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
//        setPager()
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService
        Intent(this, StepCounterService::class.java).also { intent ->
            startService(intent)
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
            serviceIntent = intent
        }
    }

    override fun onPause() {
        super.onPause()
        unbindService(mConnection)
    }

    private fun setPager() {

        val pageOne = PageOneFragment().apply { setPagerListener(this@PagerActivity) }
        val pageThree = PageThreeFragment()

        val fragmentList = ArrayList<Fragment>().apply {
            add(pageOne)
            add(pageTwo)
            add(SearchFragment())
        }
        main_pager.adapter = PagerAdapter(supportFragmentManager, fragmentList)


    }

    override fun onStepsReceived(steps: Int) {
        counter_text.text = steps.toString()
    }

    private var stepCounterService: StepCounterService? = null
    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as StepCounterService.StepServiceBinder
            stepCounterService = binder.stepsServiceInstance
            stepCounterService?.setStepsListener(this@PagerActivity)
        }
    }
}
