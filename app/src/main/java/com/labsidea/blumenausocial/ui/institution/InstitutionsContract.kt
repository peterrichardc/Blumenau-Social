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

interface InstitutionsContract{

    interface View: BaseContract.View {
        //Show the progress bar.
        infix fun showProgress(show: Boolean)

        //Show the error message in UI.
        infix fun showErrorMessage(error: String)

        //Show the data loaded in UI.
        infix fun loadDataSuccess(adapter: InstitutionsAdapter)
    }

    interface Presenter: BaseContract.Presenter<View> {

        //Load additional data.
        fun loadAdditionalData(context: Context, onClickItem: (institution: Organization?) -> Unit)

        fun filterOrganizations(filters: List<ItemsSelected>)

        fun filters(): List<ItemsSelected>
    }
}