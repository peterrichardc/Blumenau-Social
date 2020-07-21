/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution

import android.content.Context
import com.labsidea.blumenausocial.api.APIServiceInterface
import com.labsidea.blumenausocial.models.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class InstitutionsPresenter : InstitutionsContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: APIServiceInterface = APIServiceInterface.create()
    private lateinit var view: InstitutionsContract.View

    private var allOrganization: MutableList<Organization?> = mutableListOf()

    private var adapter: InstitutionsAdapter? = null
    private var onClickItem: (organization: Organization?) -> Unit = {}
    private var currentFilters: List<ItemsSelected> = mutableListOf()
    private lateinit var context: Context

    override fun subscribe() {}

    override fun unsubscribe() = subscriptions.clear()

    override fun attach(view: InstitutionsContract.View) {
        this.view = view
    }

    override fun loadAdditionalData(context: Context, onClickItem: (institution: Organization?) -> Unit) {
        val realm = Realm.getDefaultInstance()

        //TODO CACHE -> When haven't internet connection should show institutions saved in internal database.
        val subscribe = api.getInstitutionsAdditionalInformationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { t ->

                    //Merge all results and save into database.
                    Observable.merge(Observable.just(t.causes), Observable.just(t.donations), Observable.just(t.neighborhoods), Observable.just(t.volunteers))
                            .map { table ->
                                realm.beginTransaction()
                                realm.insertOrUpdate(table)
                                realm.commitTransaction()
                                t
                            }
                            .subscribe({}, { this.onError(it.message) })

                    t
                }
                .subscribe({
                    this.onClickItem = onClickItem
                    this.context = context
                    loadData()
                }, { this.onError(it.message) })

        subscriptions.add(subscribe)
    }

    private fun onError(message: String?){
        view showProgress false
        if (message != null)
            view showErrorMessage message
    }

    override fun loadData() {
        val realm = Realm.getDefaultInstance()

        val subscribe = api.getInstitutionsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .map { t -> // Save into database.
                    if (t.institutions.isNotEmpty()) {

                        allOrganization.clear()
                        t.institutions.forEach {item ->
                            item?.institution?.selected_causes = item?.selected_causes
                            item?.institution?.selected_days = item?.selected_days
                            item?.institution?.selected_periods = item?.selected_periods

                            allOrganization.add(item?.institution)
                        }

                        realm.beginTransaction()
                        realm.insertOrUpdate(allOrganization)
                        realm.commitTransaction()
                    }
                    t
                }
                .subscribe({
                    view showProgress false

                    if (it != null) {
                        adapter = InstitutionsAdapter(this.context, allOrganization, this.onClickItem)
                        view loadDataSuccess adapter!!
                    }

                }, { this.onError(it.message)})

        subscriptions.add(subscribe)
    }

    /**
     * Filter the list with organization that fits with selected screen filters.
     */
    override fun filterOrganizations(filters: List<ItemsSelected>){
        if (filters.isNotEmpty()) {
            var matchFilter: Boolean
            val listFiltered = allOrganization.filter { organization ->
                matchFilter = false

                //Check if filter match with organization.
                var canShow = true

                canShow = filters.filter { it.type == FiltersType.NEIGHBORHOODS }.any{ it.id == organization?.neighborhood }

                if (!canShow)
                    canShow = filters.filter { it.type == FiltersType.CAUSES }.any{ item -> organization?.causes!!.any { cause -> cause!!.toInt() == item.id} }

                if (!canShow)
                    canShow = filters.filter { it.type == FiltersType.DONATIONS }.any{ item -> organization?.donation_type!!.any { donation -> donation!!.toInt() == item.id} }

                if (!canShow)
                    canShow = filters.filter { it.type == FiltersType.VOLUNTEERS }.any{ item -> organization?.volunteer_type!!.any { volunteer -> volunteer!!.toInt() == item.id} }

                matchFilter = canShow

                matchFilter
            }

            this.adapter?.list = listFiltered
        }
        else
            this.adapter?.list = allOrganization

        this.currentFilters = filters
        this.adapter?.notifyDataSetChanged()
    }

    override fun filters() = this.currentFilters
}