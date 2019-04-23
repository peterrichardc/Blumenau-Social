/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution

import android.content.Context
import com.labsidea.blumenausocial.api.APIServiceInterface
import com.labsidea.blumenausocial.models.OrganizationList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class InstitutionPresenter : InstitutionContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: APIServiceInterface = APIServiceInterface.create()

    private lateinit var view: InstitutionContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: InstitutionContract.View) {
        this.view = view
    }


    override fun loadAdditionalData(context: Context) {
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
                            .subscribe()


                    t
                }
                .subscribe({
                    loadData()


                }, { error ->
                    view showProgress false

                    view showErrorMessage error.localizedMessage
                })

        subscriptions.add(subscribe)
    }

    override fun loadData() {
        val realm = Realm.getDefaultInstance()

        val subscribe = api.getInstitutionsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .map { t -> // Save into database.
                    if (t.institutions!!.isNotEmpty()) {
                        realm.beginTransaction()
                        realm.insertOrUpdate(t.institutions.toMutableList())
                        realm.commitTransaction()
                    }

                    t
                }
                .subscribe({ list: OrganizationList? ->
                    view showProgress false
                    view loadDataSuccess list!!.institutions!!.take(10)

                }, { error ->
                    view showProgress false
                    view showErrorMessage error.localizedMessage
                })

        subscriptions.add(subscribe)
    }

}