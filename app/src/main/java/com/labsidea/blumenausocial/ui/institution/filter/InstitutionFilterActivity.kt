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
import javax.inject.Inject

class InstitutionFilterActivity: AppCompatActivity(), InstitutionFilterContract.View, InstitutionsFiltersAdapter.InstitutionsFiltersAdapterEvents{

    @Inject
    lateinit var presenter: InstitutionFilterContract.Presenter

    private lateinit var adapter: InstitutionsFiltersAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_institution_filter)

        injectDependency()

        presenter attach this

        presenter.loadData()

        btnClose.setOnClickListener { finishActivity(arrayListOf()) }

        btnFilter.setOnClickListener { finishActivity(this.adapter.itemsSelected) }
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

    override fun showErrorMessage(error: String) = showProgress(false)

    override fun loadDataSuccess(neighborhoods: List<Neighborhood>, causes: List<Causes>, donations: List<Donations>, volunteers: List<Volunteers>) {
        val listHeader = mutableListOf<InstitutionsFilterHeader>()

        listHeader.add(InstitutionsFilterHeader(R.drawable.ic_neighboorhod,
                "Bairros de Blumenau",
                "Filtre os bairros que se encaixam na pesquisa",
                FiltersType.NEIGHBORHOODS))

        val listItem = hashMapOf<InstitutionsFilterHeader, List<ItemAdapter>>()

        val filters = intent.getParcelableArrayListExtra<ItemsSelected>("currentFilters")

        val list = mutableListOf<ItemAdapter>()
        neighborhoods.forEach {
            list.add(ItemAdapter(it.id, it.name, findRecordInCurrentFilter(filters, it.id, FiltersType.NEIGHBORHOODS)))
        }
        listItem.put(listHeader.last(), list)


        listHeader.add(InstitutionsFilterHeader(R.drawable.ic_causes,
                "Áreas de atuação",
                "Filtre as áreas de atuação que se encaixam na pesquisa",
                FiltersType.CAUSES))
        val list2 = mutableListOf<ItemAdapter>()
        causes.forEach {
            list2.add(ItemAdapter(it.id, it.name, findRecordInCurrentFilter(filters, it.id, FiltersType.CAUSES)))
        }
        listItem.put(listHeader.last(), list2)

        listHeader.add(InstitutionsFilterHeader(R.drawable.ic_donations,
                "Doações",
                "Filtre os tipos de doações que se encaixam na pesquisa",
                FiltersType.DONATIONS))

        val list3 = mutableListOf<ItemAdapter>()
        donations.forEach {
            list3.add(ItemAdapter(it.id, it.name, findRecordInCurrentFilter(filters, it.id, FiltersType.DONATIONS)))
        }
        listItem.put(listHeader.last(), list3)

        listHeader.add(InstitutionsFilterHeader(R.drawable.ic_volunteers,
                "Voluntários",
                "Filtre os tipos de voluntários que se encaixam na pesquisa",
                FiltersType.VOLUNTEERS))
        val list4 = mutableListOf<ItemAdapter>()
        volunteers.forEach {
            list4.add(ItemAdapter(it.id, it.name, findRecordInCurrentFilter(filters, it.id, FiltersType.VOLUNTEERS)))
        }
        listItem.put(listHeader.last(), list4)

        this.adapter = InstitutionsFiltersAdapter(this, listHeader, listItem, this)


        expdFilters.setAdapter(this.adapter)

    }

    override fun onClickExpandOrCollapse(groupPosition: Int) {
        if (expdFilters.isGroupExpanded(groupPosition))
            expdFilters.collapseGroup(groupPosition)
        else
            expdFilters.expandGroup(groupPosition)

    }


    private fun findRecordInCurrentFilter(filters: List<ItemsSelected>, id: Int, type: FiltersType): Int{
        val find = filters.any { filter -> filter.type == type && filter.id  == id }

        return if (find) 1 else 0
    }






}