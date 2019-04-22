/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.di.component.DaggerFragmentComponent
import com.labsidea.blumenausocial.di.module.FragmentModule
import com.labsidea.blumenausocial.models.Organization
import kotlinx.android.synthetic.main.fragment_institutions.*
import javax.inject.Inject
import android.view.WindowManager
import com.labsidea.blumenausocial.ui.institution.detail.InstitutionDetailActivity
import com.labsidea.blumenausocial.ui.institution.filter.InstitutionFilterActivity
import org.jetbrains.anko.support.v4.startActivity


class InstitutionsFragment: Fragment(), InstitutionContract.View, InstitutionAdapter.InstitutionAdapterEvents{


    @Inject lateinit var presenter: InstitutionContract.Presenter

    companion object {
        const val TAG: String = "InstitutionsFragment"
    }

    fun newInstance(): InstitutionsFragment {
        return InstitutionsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_institutions, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        injectDependency()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter attach this

        presenter.subscribe()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.unsubscribe()
    }

    override fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorMessage(error: String) {
        Log.e("Error", error)
    }

    override fun loadDataSuccess(list: List<Organization>) {
        val newHeight = main_container.height - (btnFilter.height + 10)
        val behavior = BottomSheetBehavior.from(rvInstitutions)
    //    behavior.setPeekHeight(newHeight)
//        behavior.isHideable = false
//        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED)

        rvInstitutions!!.layoutManager = LinearLayoutManager(context)
        val adapter = InstitutionAdapter(context!!, list.toMutableList(), this)
        rvInstitutions!!.adapter = adapter
    }

    override fun onClickItem(institution: Organization) {
        startActivity<InstitutionDetailActivity>("institution_id_selected" to institution.id)

    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build()

        listComponent.inject(this)
    }

    private fun initView() {
        presenter.loadAdditionalData(context!!)

        btnFilter.setOnClickListener{ startActivity<InstitutionFilterActivity>()}
    }




}