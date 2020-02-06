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

    private var allOrganization: List<Organization?> = mutableListOf()

    private var adapter: InstitutionsAdapter? = null
    private var onClickItem: (organization: Organization?) -> Unit = {}
    private var currentFilters: List<ItemsSelected> = mutableListOf()

    override fun subscribe() {}

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: InstitutionsContract.View) {
        this.view = view
    }

    override fun loadAdditionalData(context: Context, onClickItem: (institution: Organization?) -> Unit) {
        val realm = Realm.getDefaultInstance()

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
                        realm.beginTransaction()
                        realm.insertOrUpdate(t.institutions.toMutableList())
                        realm.commitTransaction()
                    }
                    t
                }
                .subscribe({
                    view showProgress false

                    if (it != null) {
                        allOrganization = it.institutions
                        adapter = InstitutionsAdapter(it.institutions, this.onClickItem)
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
                val filterNeighborhood = filters.filter { it.type == FiltersType.NEIGHBORHOODS }.filter { it.id == organization?.neighborhood }
                matchFilter = filterNeighborhood.isNotEmpty()

                matchFilter
            }

            this.currentFilters = filters
            this.adapter?.list = listFiltered
            this.adapter?.notifyDataSetChanged()
        }
    }

    override fun filters() = this.currentFilters
}