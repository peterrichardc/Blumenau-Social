/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution

import android.app.Activity
import android.content.Intent
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
import com.labsidea.blumenausocial.models.ItemsSelected
import com.labsidea.blumenausocial.ui.institution.detail.InstitutionDetailActivity
import com.labsidea.blumenausocial.ui.institution.filter.InstitutionFilterActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult


class InstitutionsFragment: Fragment(), InstitutionContract.View, InstitutionAdapter.InstitutionAdapterEvents{


    @Inject lateinit var presenter: InstitutionContract.Presenter

    private lateinit var adapter: InstitutionAdapter
    private var actuallyFilters: List<ItemsSelected> = mutableListOf()

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
        showProgress(false)
    }

    override fun loadDataSuccess(list: List<Organization>) {
        val newHeight = main_container.height - (btnFilter.height + 10)
        val behavior = BottomSheetBehavior.from(rvInstitutions)
    //    behavior.setPeekHeight(newHeight)
//        behavior.isHideable = false
//        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED)

        rvInstitutions!!.layoutManager = LinearLayoutManager(context)
        this.adapter = InstitutionAdapter(context!!, list.toMutableList(), this)
        rvInstitutions!!.adapter = this.adapter
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

        btnFilter.setOnClickListener{
            startActivityForResult<InstitutionFilterActivity>(1, "currentFilters" to this.actuallyFilters)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1){
            if (data != null && data.hasExtra("items_filter")){
                this.actuallyFilters = data.getParcelableArrayListExtra("items_filter")

                changeAdapter(this.presenter.filterOrganizations(this.actuallyFilters, this.adapter.list))
            }
        }
    }

    private fun changeAdapter(newListToAdapter: List<Organization>){
        this.adapter.list = newListToAdapter
        this.adapter.notifyDataSetChanged()
    }




}