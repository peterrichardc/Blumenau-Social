/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution

import android.content.Context
import com.labsidea.blumenausocial.models.ItemsSelected
import com.labsidea.blumenausocial.models.Organization
import com.labsidea.blumenausocial.ui.base.BaseContract

interface InstitutionContract{

    interface View: BaseContract.View {
        //Show the progress bar.
        infix fun showProgress(show: Boolean)

        //Show the error message in UI.
        infix fun showErrorMessage(error: String)

        //Show the data loaded in UI.
        infix fun loadDataSuccess(list: List<Organization>)
    }

    interface Presenter: BaseContract.Presenter<View> {
        //Load additional data.
        infix fun loadAdditionalData(context: Context)

        fun filterOrganizations(filters: List<ItemsSelected>, listToBeFiltered: List<Organization>): List<Organization>
    }
}