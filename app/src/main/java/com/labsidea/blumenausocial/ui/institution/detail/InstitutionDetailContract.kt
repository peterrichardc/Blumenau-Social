/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution.detail

import com.labsidea.blumenausocial.models.Organization
import com.labsidea.blumenausocial.ui.base.BaseContract

interface InstitutionDetailContract{

    interface View: BaseContract.View {
        //Show the progress bar.
        infix fun showProgress(show: Boolean)

        //Show the error message in UI.
        infix fun showErrorMessage(error: String)

        //Show the data loaded in UI.
        infix fun loadDataSuccess(donationsAdapter: DonationsAdapter)
    }

    interface Presenter: BaseContract.Presenter<View>{
        var institution: Organization?

        fun loadData()
    }
}