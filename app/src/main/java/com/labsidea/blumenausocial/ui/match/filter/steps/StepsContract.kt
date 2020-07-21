/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/


package com.labsidea.blumenausocial.ui.match.filter.steps

import android.content.Context
import com.labsidea.blumenausocial.models.Organization
import com.labsidea.blumenausocial.ui.base.BaseContract
import com.labsidea.blumenausocial.ui.institution.filter.FilterAdapter

interface StepsContract{

    interface View : BaseContract.View{
        //Show the data loaded in UI.
        fun onLoadAdapter(adapter: FilterAdapter)
    }

    interface Presenter : BaseContract.Presenter<View>
    {
        infix fun loadNeighborhoodAdapter(context: Context)
        infix fun loadDaysOfWeekAdapter(context: Context)
        infix fun loadTimesOfDayAdapter(context: Context)
        infix fun loadCausesAdapter(context: Context)
    }
}