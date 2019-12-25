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
import com.labsidea.blumenausocial.models.Organization
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

    }

    override fun showErrorMessage(error: String) {
        showProgress(false)
    }

    override fun loadDataSuccess(institution: Organization?) {
        tvInstitutionName?.text = institution?.title

        tvSubTitle?.text = institution?.subtitle

        tvAddress?.text = institution?.address

        tvPhone?.text = institution?.phone
        tvPhone?.setOnClickListener {
            if (institution != null)
                makeCall(institution.phone)
        }

        tvMail?.text = institution?.mail
        tvMail?.setOnClickListener {
            if (institution != null)
                email(institution.mail, institution.title)
        }

        tvWorkingHours?.text = institution?.working_hours

        tvResponsible?.text = getString(R.string.responsible).format(Locale.getDefault(), institution?.responsible)

        tvAbout?.text = institution?.about?.first()

        tvVolunteers?.text = institution?.volunteers

        if (institution?.donations != null) {
            rvDonations.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rvDonations.adapter = DonationsAdapter(institution.donations)
        }

    }


}