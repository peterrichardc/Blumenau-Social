/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.main

import io.reactivex.disposables.CompositeDisposable

class MainPresenter: MainContract.Presenter {

    private val subscriptions = CompositeDisposable()

    private lateinit var view: MainContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun loadData() {
        view.showInstitutionsFragment() // as default
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun onClickAboutMenu() {
        view.showAboutFragment()
    }
}