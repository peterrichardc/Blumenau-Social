package com.labsidea.blumenausocial.ui.match.filter

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.ui.match.filter.steps.StepsPagerAdapter
import kotlinx.android.synthetic.main.activity_match_filters.*

class MatchFiltersActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_filters)

        initView()
    }

    private fun initView() {
        vpFilters.adapter = StepsPagerAdapter(supportFragmentManager, ::onNextPage)
        indicator.setViewPager(vpFilters)

        btnClose.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun onNextPage(){
        val index = vpFilters.currentItem + 1

        if (index == 3) { //Last page index.
            startMatch()
        }

        vpFilters.setCurrentItem(index, true)
    }

    private fun startMatch(){
        setResult(Activity.RESULT_OK)
        finish()
    }
}