package org.mab.playground.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mab.playground.Constants.Constants
import org.mab.playground.R
import org.mab.playground.preferences.AppPreferences

class PageThreeFragment : Fragment() {

    private val appPreference by lazy {
        AppPreferences(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_three, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!appPreference.getModeVisitTrack().contains(Constants.tag)) {
            appPreference.addModeVisitTrack(Constants.tag)
        }
    }
}