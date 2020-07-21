package com.labsidea.blumenausocial.ui.match.filter.steps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import kotlinx.android.synthetic.main.fragment_match_step1.*

class StepOneFragment: Fragment(){

    lateinit var onNextPage: () -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_match_step1, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvLetsGo.setOnClickListener { this.onNextPage() }
    }
}