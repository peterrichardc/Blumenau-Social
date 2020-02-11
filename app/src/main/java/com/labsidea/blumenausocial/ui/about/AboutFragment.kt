package com.labsidea.blumenausocial.ui.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment: Fragment(){

    fun newInstance() = AboutFragment()

    companion object {
        const val TAG: String = "AboutFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_about, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpMain.adapter = AboutPagerAdapter(childFragmentManager)
        indicator.setViewPager(vpMain)
    }

}