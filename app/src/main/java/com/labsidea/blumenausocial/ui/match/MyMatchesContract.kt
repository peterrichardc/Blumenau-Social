/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/


package com.labsidea.blumenausocial.ui.match
import android.content.Context
import com.labsidea.blumenausocial.ui.base.BaseContract
import com.labsidea.blumenausocial.ui.institution.detail.DonationsAdapter

interface MyMatchesContract{

    interface View : BaseContract.View{
        //Show the data loaded in UI.
        infix fun onMatchReady(adapter: MyMatchesPagerAdapter)

        fun onNeedMakeMatch()

        infix fun onClickInstitution(id: Int)
    }

    interface Presenter : BaseContract.Presenter<View>
    {
        fun makeMatchInstitutions(context: Context)

        infix fun getDonationsAdapter(institutionIndex: Int): DonationsAdapter
    }
}