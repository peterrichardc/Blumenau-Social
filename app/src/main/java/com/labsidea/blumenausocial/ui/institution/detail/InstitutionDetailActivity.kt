/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.di.component.DaggerActivityComponent
import com.labsidea.blumenausocial.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_institution_detail.*
import org.jetbrains.anko.email
import org.jetbrains.anko.makeCall
import java.util.*
import javax.inject.Inject

class InstitutionDetailActivity : AppCompatActivity(), InstitutionDetailContract.View {


    @Inject
    lateinit var presenter: InstitutionDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_institution_detail)

        injectDependency()
        presenter attach this
        presenter.loadData()

        btnClose.setOnClickListener { finish() }
    }


    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    override fun showProgress(show: Boolean) {
        //TODO Add progress bar.

    }

    override fun showErrorMessage(error: String) {
        showProgress(false)
    }

    override fun loadDataSuccess(donationsAdapter: DonationsAdapter) {
        tvInstitutionName?.text = presenter.institution?.title
        tvSubTitle?.text = presenter.institution?.subtitle
        tvAddress?.text = presenter.institution?.address
        tvPhone?.text = presenter.institution?.phone
        tvMail?.text = presenter.institution?.mail
        tvWorkingHours?.text = presenter.institution?.working_hours
        tvResponsible?.text = getString(R.string.responsible).format(Locale.getDefault(), presenter.institution?.responsible)
        tvAbout?.text = presenter.institution?.about?.first()
        tvVolunteers?.text = presenter.institution?.volunteers

        tvPhone?.setOnClickListener {
            if (presenter.institution != null)
                makeCall(presenter.institution!!.phone)
        }

        tvMail?.setOnClickListener {
            if (presenter.institution != null)
                email(presenter.institution!!.mail, presenter.institution!!.title)
        }

        rvDonations.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvDonations.adapter = donationsAdapter

    }


}