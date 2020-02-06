/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.di.component.DaggerActivityComponent
import com.labsidea.blumenausocial.di.module.ActivityModule
import com.labsidea.blumenausocial.ui.about.AboutFragment
import com.labsidea.blumenausocial.ui.institution.InstitutionsFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject lateinit var presenter: MainContract.Presenter

    //Main menu click.
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_institutions -> {
                presenter.onClickInstitutionsMenu()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_match -> {
                //message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                presenter.onClickAboutMenu()
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        injectDependency()

        presenter attach this


        //show the first fragment.
        presenter.loadData()

    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    override fun showAboutFragment() {
        supportFragmentManager.beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.frmMain, AboutFragment().newInstance(), AboutFragment.TAG)
                .commit()
    }

    override fun showInstitutionsFragment() {
        supportFragmentManager.beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.frmMain, InstitutionsFragment().newInstance(), InstitutionsFragment.TAG)
                .commit()

    }


}
