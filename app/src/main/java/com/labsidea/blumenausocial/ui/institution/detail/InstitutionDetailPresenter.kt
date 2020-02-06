/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution.detail

import com.labsidea.blumenausocial.models.Organization
import io.realm.Realm
import java.lang.Exception

class InstitutionDetailPresenter(private val institution_id_selected: Int): InstitutionDetailContract.Presenter{

    private lateinit var view: InstitutionDetailContract.View

    private var currentInstitution: Organization? = Organization()

    override var institution: Organization?
        get() = this.currentInstitution
        set(_) {}

    /**
     * Apply select in Realm Organization Table, and return record that match with id referenced by parameter.
    */
    override fun loadData() {
        try {
            view.showProgress(true)

            val realm = Realm.getDefaultInstance()
            currentInstitution = realm.where(Organization::class.java).equalTo("id", institution_id_selected).findFirst()
            val donationsAdapter = DonationsAdapter(currentInstitution!!.donations)

            view loadDataSuccess donationsAdapter
            view showProgress false
        }catch (e: Exception){

            view showProgress false

            if (e.message != null)
                view showErrorMessage e.message!!
        }
    }

    override fun subscribe() {}

    override fun unsubscribe() {}

    override fun attach(view: InstitutionDetailContract.View) {
        this.view = view
    }

}