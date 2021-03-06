/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.main

class MainPresenter: MainContract.Presenter {

    private lateinit var view: MainContract.View

    override fun subscribe() {}
    override fun unsubscribe() {}

    override fun init() {
        view.showInstitutionsFragment() // as default
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun onClickInstitutionsMenu() = view.showInstitutionsFragment()

    override fun onClickMyMatchesMenu() = view.showMyMatchs()

    override fun onClickAboutMenu() = view.showAboutFragment()
}