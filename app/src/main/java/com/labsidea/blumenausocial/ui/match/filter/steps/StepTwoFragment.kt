package com.labsidea.blumenausocial.ui.match.filter.steps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.di.component.DaggerFragmentComponent
import com.labsidea.blumenausocial.di.module.FragmentModule
import com.labsidea.blumenausocial.ui.institution.filter.FilterAdapter
import kotlinx.android.synthetic.main.fragment_match_step2.*
import javax.inject.Inject

class StepTwoFragment: Fragment(), StepsContract.View{

    lateinit var onNextPage: () -> Unit

    @Inject
    lateinit var presenter: StepsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_match_step2, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependency()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter attach this
        presenter loadNeighborhoodAdapter context!!

        tvAlmostThere.setOnClickListener { this.onNextPage() }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.unsubscribe()
    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build()

        listComponent.inject(this)
    }

    override fun onLoadAdapter(adapter: FilterAdapter) {
        rvNeighborhoods?.adapter = adapter
        if (rvNeighborhoods?.layoutManager == null)
            rvNeighborhoods?.layoutManager = GridLayoutManager(context, 3)

    }
}