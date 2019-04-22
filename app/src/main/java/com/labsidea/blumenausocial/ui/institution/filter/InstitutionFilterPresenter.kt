/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution.filter

import com.labsidea.blumenausocial.models.*
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function4
import io.realm.Realm
import io.realm.RealmResults


class InstitutionFilterPresenter : InstitutionFilterContract.Presenter {

    private val subscriptions = CompositeDisposable()

    private lateinit var view: InstitutionFilterContract.View

    override fun subscribe() {
    }

    override fun unsubscribe() {
        subscriptions.clear()

    }

    override fun attach(view: InstitutionFilterContract.View) {
        this.view = view
    }

    override fun loadData() {

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

                    view.loadDataSuccess(model!!.neighborhoods!!, model.causes!!, model.donations!!, model.volunteers!!)
                }, { error ->
                    view showProgress false

                    view showErrorMessage error.localizedMessage
                })

        subscriptions.add(subs)

    }


    private fun createOrganizationAdditionalInformationList(neighborhoods: List<Neighborhood>, causes: List<Causes>, donations: List<Donations>, volunteers: List<Volunteers>) = OrganizationAdditionalInformationList(neighborhoods, causes, donations, volunteers)


}
