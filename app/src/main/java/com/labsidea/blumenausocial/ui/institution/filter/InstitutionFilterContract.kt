/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/


package com.labsidea.blumenausocial.ui.institution.filter

import com.labsidea.blumenausocial.models.ItemsSelected
import com.labsidea.blumenausocial.ui.base.BaseContract

interface InstitutionFilterContract{

    interface View : BaseContract.View{
        //Show the progress bar.
        infix fun showProgress(show: Boolean)

        //Show the error message in UI.
        infix fun showErrorMessage(error: String)

        //Show the data loaded in UI.
        infix fun loadDataSuccess(filtersAdapter: InstitutionsFiltersAdapter)
    }

    interface Presenter : BaseContract.Presenter<View>{
        fun loadData(onClickExpandOrCollapse: (Int) -> Unit?)

        fun filters(): List<ItemsSelected>
    }
}