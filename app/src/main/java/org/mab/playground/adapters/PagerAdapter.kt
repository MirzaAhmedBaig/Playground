package org.mab.playground.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter


/**
 * Created by Mirza Ahmed Baig on 29/11/18.
 * Avantari Technologies
 * mirza@avantari.org
 */

class PagerAdapter(fragmentManager: FragmentManager, private val fragmentList: ArrayList<Fragment>) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(p0: Int) = fragmentList[p0]

    override fun getCount() = fragmentList.size

    override fun getItemPosition(`object`: Any) = POSITION_NONE

    fun removeAt(position: Int) {
        fragmentList.removeAt(position)
        notifyDataSetChanged()
    }

    fun addAt(position: Int, fragment: Fragment) {
        fragmentList.add(position, fragment)
        notifyDataSetChanged()
    }

}