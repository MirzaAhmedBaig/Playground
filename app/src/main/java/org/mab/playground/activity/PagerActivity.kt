package org.mab.playground.activity

import android.os.Bundle
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

class PagerActivity : AppCompatActivity(), PagerOperationListener {
    private val pagerAdapter by lazy {
        main_pager.adapter as PagerAdapter
    }
    private val appPreference by lazy {
        AppPreferences(this)
    }

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
        setPager()
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
}
