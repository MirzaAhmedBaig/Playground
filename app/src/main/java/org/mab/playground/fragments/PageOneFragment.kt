package org.mab.playground.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_page_one.*
import org.mab.playground.Constants.Constants

import org.mab.playground.R
import org.mab.playground.listeners.PagerOperationListener


class PageOneFragment : Fragment() {


    private var pagerOperationListener: PagerOperationListener? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    fun setPagerListener(pagerOperationListener: PagerOperationListener) {
        this.pagerOperationListener = pagerOperationListener
    }

    private fun setListener() {
        button1.setOnClickListener {
            Constants.tag = "one"
            pagerOperationListener?.onRightSwipeRequest("one")

        }
        button2.setOnClickListener {
            Constants.tag = "two"
            pagerOperationListener?.onRightSwipeRequest("two")
        }
        button3.setOnClickListener {
            Constants.tag = "three"
            pagerOperationListener?.onRightSwipeRequest("three")
        }
    }
}
