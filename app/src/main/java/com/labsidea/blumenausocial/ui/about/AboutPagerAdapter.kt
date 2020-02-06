package com.labsidea.blumenausocial.ui.about

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class AboutPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){

    private val list = mutableListOf<Fragment>()

    init {
        list.add(AboutStep1Fragment())
        list.add(AboutStep2Fragment())
        list.add(AboutStep3Fragment())
        list.add(AboutStep4Fragment())
    }

    override fun getItem(index: Int): Fragment  = this.list[index]

    override fun getCount() = this.list.size

}