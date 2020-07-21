/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.main

import com.labsidea.blumenausocial.ui.base.BaseContract

//Interface to be implemented by Main Activity.
class MainContract {

    interface View: BaseContract.View {
        //Show the Institutions Fragment.
        fun showInstitutionsFragment()

        //Show the My Matchs Fragment.
        fun showMyMatchs()

        //Show the About Fragment.
        fun showAboutFragment()

    }

    interface Presenter: BaseContract.Presenter<MainContract.View> {
        fun init()

        //When the institutions menu is clicked.
        fun onClickInstitutionsMenu()

        //When the My Matches menu is clicked.
        fun onClickMyMatchesMenu()

        //When the about menu is clicked.
        fun onClickAboutMenu()
    }
}