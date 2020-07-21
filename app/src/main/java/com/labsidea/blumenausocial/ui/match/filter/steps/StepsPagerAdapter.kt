package com.labsidea.blumenausocial.ui.match.filter.steps

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class StepsPagerAdapter(fm: FragmentManager, onNextPage: () -> Unit): FragmentStatePagerAdapter(fm){

    private val fragments = mutableListOf<Fragment>()

    init {
        val stepOne = StepOneFragment()
        stepOne.onNextPage = onNextPage
        fragments.add(stepOne)

        val stepTwo = StepTwoFragment()
        stepTwo.onNextPage = onNextPage
        fragments.add(stepTwo)

        //val stepThree = StepThreeFragment()
        //stepThree.onNextPage = onNextPage
        //fragments.add(stepThree)

        val stepFour = StepFourFragment()
        stepFour.onNextPage = onNextPage
        fragments.add(stepFour)
    }

    override fun getItem(index: Int): Fragment  = this.fragments[index]

    override fun getCount() = this.fragments.size

}