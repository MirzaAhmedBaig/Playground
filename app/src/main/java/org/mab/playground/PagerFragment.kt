package org.mab.playground

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_pager.*


class PagerFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name.text = arguments?.getString("name")

    }


    companion object {

        @JvmStatic
        fun newInstance(name: String) =
                PagerFragment().apply {
                    arguments = Bundle().apply {
                        putString("name", name)
                    }
                }
    }
}
