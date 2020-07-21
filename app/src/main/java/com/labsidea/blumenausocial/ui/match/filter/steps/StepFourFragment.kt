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
import kotlinx.android.synthetic.main.fragment_match_step4.*
import javax.inject.Inject

class StepFourFragment: Fragment(), StepsContract.View{
    lateinit var onNextPage: () -> Unit

    @Inject
    lateinit var presenter: StepsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_match_step4, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependency()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter attach this
        presenter loadCausesAdapter context!!

        tvReady.setOnClickListener { this.onNextPage() }
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
        rvCauses?.adapter = adapter
        if (rvCauses?.layoutManager == null)
            rvCauses?.layoutManager = GridLayoutManager(context, 3)
    }
}