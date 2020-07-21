/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution.filter

import android.content.Context
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.models.*
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function4
import io.realm.Realm
import io.realm.RealmResults

@Suppress("NON_EXHAUSTIVE_WHEN")
class InstitutionFilterPresenter(val context: Context, var currentFilters: MutableList<ItemsSelected>?) : InstitutionFilterContract.Presenter {
    private val subscriptions = CompositeDisposable()
    private lateinit var view: InstitutionFilterContract.View

    private val listHeader = mutableListOf<InstitutionsFilterHeader>()
    private val listItem = hashMapOf<InstitutionsFilterHeader, List<ItemAdapter>>()
    private lateinit var onClickExpandOrCollapse: (Int) -> Unit?

    override fun subscribe() {}

    override fun unsubscribe() = subscriptions.clear()

    override fun attach(view: InstitutionFilterContract.View) {
        this.view = view
    }

    override fun loadData(onClickExpandOrCollapse: (Int) -> Unit? ) {
        val realm = Realm.getDefaultInstance()

        //Select and Zip the tables :Causes, Donations, Volunteers and Neighborhoods, and return them to UI.
        val subs = Flowable.zip(realm.where(Neighborhood::class.java).findAllAsync().asFlowable().filter { isLoaded -> isLoaded.isLoaded },
                realm.where(Causes::class.java).findAllAsync().asFlowable().filter { isLoaded -> isLoaded.isLoaded },
                realm.where(Donations::class.java).findAllAsync().asFlowable().filter { isLoaded -> isLoaded.isLoaded },
                realm.where(Volunteers::class.java).findAllAsync().asFlowable().filter { isLoaded -> isLoaded.isLoaded },
                Function4 { neighborhoods: RealmResults<Neighborhood>, causes: RealmResults<Causes>, donations: RealmResults<Donations>, volunteers: RealmResults<Volunteers> ->
                    createOrganizationAdditionalInformationList(neighborhoods, causes, donations, volunteers)
                })
                .subscribe({ model: OrganizationAdditionalInformationList? ->
                    view showProgress false

                    this.onClickExpandOrCollapse = onClickExpandOrCollapse
                    view loadDataSuccess makeAdapterFilters(model)
                }, { error ->
                    view showProgress false

                    view showErrorMessage error.localizedMessage
                })
        subscriptions.add(subs)
    }

    override fun filters() = this.currentFilters?.toList() ?: mutableListOf()

    private fun createOrganizationAdditionalInformationList(neighborhoods: List<Neighborhood>, causes: List<Causes>, donations: List<Donations>, volunteers: List<Volunteers>) = OrganizationAdditionalInformationList(neighborhoods, causes, donations, volunteers)

    private fun addFilter(filters: List<Any>?, iconR: Int, title: String, subTitle: String, type: FiltersType){
        listHeader.add(InstitutionsFilterHeader(iconR, title, subTitle, type))
        val list = mutableListOf<ItemAdapter>()
        filters?.forEach {
            when(type){
                FiltersType.NEIGHBORHOODS -> list.add(ItemAdapter((it as Neighborhood).id, it.name, findRecordInCurrentFilter(currentFilters!!, it.id, type), it.image))
                FiltersType.CAUSES        -> list.add(ItemAdapter((it as Causes      ).id, it.name, findRecordInCurrentFilter(currentFilters!!, it.id, type), it.image))
                FiltersType.DONATIONS     -> list.add(ItemAdapter((it as Donations   ).id, it.name, findRecordInCurrentFilter(currentFilters!!, it.id, type), it.image))
                FiltersType.VOLUNTEERS    -> list.add(ItemAdapter((it as Volunteers  ).id, it.name, findRecordInCurrentFilter(currentFilters!!, it.id, type), it.image))
            }
        }
        listItem.put(listHeader.last(), list)
    }

    private fun makeAdapterFilters(model: OrganizationAdditionalInformationList?): InstitutionsFiltersAdapter{
        addFilter(model?.neighborhoods, R.drawable.ic_neighboorhod, "Bairros de Blumenau", "Filtre as regiões que se encaixam na sua rotina.", FiltersType.NEIGHBORHOODS)
        addFilter(model?.causes       , R.drawable.ic_causes      , "Áreas de atuação"   , "As vezes um pequeno gesto pode fazer a diferença!", FiltersType.CAUSES)
        addFilter(model?.donations    , R.drawable.ic_donations   , "Doações"            , "Partilha! Aprenda a dividir para multiplicar! Pequenas partilhas geram grandes felicidades.", FiltersType.DONATIONS)
        addFilter(model?.volunteers   , R.drawable.ic_volunteers  , "Voluntários"        , "Solidariedade ao seu alcance, encontre tipos de voluntáriados que podem ser sua cara!", FiltersType.VOLUNTEERS)

        if (currentFilters == null)
            currentFilters = mutableListOf()

        return InstitutionsFiltersAdapter(context, listHeader, listItem, currentFilters!!, onClickExpandOrCollapse)
    }

    private fun findRecordInCurrentFilter(filters: List<ItemsSelected>, id: Int, type: FiltersType): Int{
        val find = filters.any { filter -> filter.type == type && filter.id  == id }

        return if (find) 1 else 0
    }
}
