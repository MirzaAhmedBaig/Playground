package org.mab.playground

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter


class EmotionsHomeFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val TAG = EmotionsHomeFragmentAdapter::class.java.simpleName

    private var PAGE_COUNT = 3
    private var showAllPages = true
    override fun getItemPosition(fragment: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }


    fun allowAllPageToShow(isAllowed: Boolean) {
        showAllPages = isAllowed
        PAGE_COUNT = 2
    }


    override fun getItem(position: Int): Fragment? =
            if (showAllPages) {
                when (position) {
                    0 -> {
                        PagerFragment.newInstance("First Fragment")
                    }
                    1 -> {
                        PagerFragment.newInstance("Second Fragment")
                    }
                    2 -> {
                        PagerFragment.newInstance("Third Fragment")
                    }
                    else -> null
                }

            } else {
                when (position) {
                    0 -> {
                        PagerFragment.newInstance("First Fragment")
                    }
                    1 -> {
                        PagerFragment.newInstance("Third Fragment")
                    }
                    else -> null
                }

            }

}