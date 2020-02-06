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
        //Show the About Fragment.
        fun showAboutFragment()

        //Show the Institutions Fragment.
        fun showInstitutionsFragment()
    }

    interface Presenter: BaseContract.Presenter<MainContract.View> {
        //When the institutions menu is clicked.
        fun onClickInstitutionsMenu()
        //When the about menu is clicked.
        fun onClickAboutMenu()
    }
}