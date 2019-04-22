/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/


package com.labsidea.blumenausocial.ui.institution.filter

import com.labsidea.blumenausocial.models.Causes
import com.labsidea.blumenausocial.models.Donations
import com.labsidea.blumenausocial.models.Neighborhood
import com.labsidea.blumenausocial.models.Volunteers
import com.labsidea.blumenausocial.ui.base.BaseContract

interface InstitutionFilterContract{

    interface View : BaseContract.View{
        //Show the progress bar.
        infix fun showProgress(show: Boolean)

        //Show the error message in UI.
        infix fun showErrorMessage(error: String)

        //Show the data loaded in UI.
        fun loadDataSuccess(neighborhoods: List<Neighborhood>, causes: List<Causes>, donations: List<Donations>, volunteers: List<Volunteers>)

    }

    interface Presenter : BaseContract.Presenter<View>
}