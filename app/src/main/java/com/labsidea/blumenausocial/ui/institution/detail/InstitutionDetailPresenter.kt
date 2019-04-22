/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution.detail

import com.labsidea.blumenausocial.models.Organization
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm

class InstitutionDetailPresenter(private val institution_id_selected: Int): InstitutionDetailContract.Presenter{

    private val subscriptions = CompositeDisposable()

    private lateinit var view: InstitutionDetailContract.View

    override fun loadData() {
        val realm = Realm.getDefaultInstance()

        val institution: Organization? = realm.where(Organization::class.java).equalTo("id", institution_id_selected).findFirst()

        view loadDataSuccess institution
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: InstitutionDetailContract.View) {
        this.view = view
    }

}