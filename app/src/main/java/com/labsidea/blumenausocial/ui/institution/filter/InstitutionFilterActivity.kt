/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/


package com.labsidea.blumenausocial.ui.institution.filter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.di.component.DaggerActivityComponent
import com.labsidea.blumenausocial.di.module.ActivityModule
import com.labsidea.blumenausocial.models.Causes
import com.labsidea.blumenausocial.models.Donations
import com.labsidea.blumenausocial.models.Neighborhood
import com.labsidea.blumenausocial.models.Volunteers
import org.jetbrains.anko.toast
import javax.inject.Inject

class InstitutionFilterActivity: AppCompatActivity(), InstitutionFilterContract.View{

    @Inject
    lateinit var presenter: InstitutionFilterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_institution_filter)

        injectDependency()

        presenter attach this

        showProgress(true)

        presenter.loadData()
    }

    private fun injectDependency(){
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }


    override fun showProgress(show: Boolean) {

    }

    override fun showErrorMessage(error: String) {

    }

    override fun loadDataSuccess(neighborhoods: List<Neighborhood>, causes: List<Causes>, donations: List<Donations>, volunteers: List<Volunteers>) {
        toast("oieeee")
    }





}