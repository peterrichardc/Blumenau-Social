/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/


package com.labsidea.blumenausocial.ui.institution.filter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.di.component.DaggerActivityComponent
import com.labsidea.blumenausocial.di.module.ActivityModule
import com.labsidea.blumenausocial.models.Causes
import com.labsidea.blumenausocial.models.Donations
import com.labsidea.blumenausocial.models.Neighborhood
import com.labsidea.blumenausocial.models.Volunteers
import kotlinx.android.synthetic.main.activity_institution_filter.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class InstitutionFilterActivity: AppCompatActivity(), InstitutionFilterContract.View{

    @Inject
    lateinit var presenter: InstitutionFilterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_institution_filter)
        //toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel)
        //toolbar.setNavigationOnClickListener { finish() }

        injectDependency()

        presenter attach this

        presenter.loadData()
    }

    private fun injectDependency(){
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }


    override fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorMessage(error: String) {
        showProgress(false)
    }

    override fun loadDataSuccess(neighborhoods: List<Neighborhood>, causes: List<Causes>, donations: List<Donations>, volunteers: List<Volunteers>) {


        val listHeader = mutableListOf<String>()

        listHeader.add("Bairros de Blumenau")

        val listItem = hashMapOf<String, List<String>>()

        val list = mutableListOf<String>()
        neighborhoods.forEach {
            list.add(it.name)
        }
        listItem.put(listHeader[listHeader.count()-1], list)


        listHeader.add("Áreas de atuação")
        val list2 = mutableListOf<String>()
        causes.forEach {
            list2.add(it.name)
        }
        listItem.put(listHeader[listHeader.count()-1], list2)

        listHeader.add("Doações")
        val list3 = mutableListOf<String>()
        donations.forEach {
            list3.add(it.name)
        }
        listItem.put(listHeader[listHeader.count()-1], list3)

        listHeader.add("Voluntários")
        val list4 = mutableListOf<String>()
        volunteers.forEach {
            list4.add(it.name)
        }
        listItem.put(listHeader[listHeader.count()-1], list4)



        expdFilters.setAdapter(InstitutionsFiltersAdapter(this, listHeader, listItem))

    }





}