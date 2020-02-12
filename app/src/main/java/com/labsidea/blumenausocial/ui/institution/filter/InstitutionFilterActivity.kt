/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/


package com.labsidea.blumenausocial.ui.institution.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.di.component.DaggerActivityComponent
import com.labsidea.blumenausocial.di.module.ActivityModule
import com.labsidea.blumenausocial.models.*
import kotlinx.android.synthetic.main.activity_institution_filter.*
import org.jetbrains.anko.alert
import javax.inject.Inject

class InstitutionFilterActivity: AppCompatActivity(), InstitutionFilterContract.View{

    @Inject
    lateinit var presenter: InstitutionFilterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_institution_filter)

        injectDependency()
        presenter attach this
        presenter.loadData(::onClickExpandOrCollapse)

        btnClose.setOnClickListener { finishActivity(arrayListOf()) }
        btnFilter.setOnClickListener { finishActivity(ArrayList(presenter.filters())) }
    }

    private fun injectDependency(){
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()
        activityComponent.inject(this)
    }

    private fun finishActivity(list: ArrayList<ItemsSelected>){
        val intent = Intent()
        intent.putParcelableArrayListExtra("items_filter", list)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorMessage(error: String){
        showProgress(false)
        alert(error, getString(R.string.error_loading))
    }

    override fun loadDataSuccess(filtersAdapter: InstitutionsFiltersAdapter) = expdFilters.setAdapter(filtersAdapter)

    private fun onClickExpandOrCollapse(groupPosition: Int) {
        if (expdFilters.isGroupExpanded(groupPosition))
            expdFilters.collapseGroup(groupPosition)
        else
            expdFilters.expandGroup(groupPosition)
    }
}