package com.labsidea.blumenausocial.ui.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import kotlinx.android.synthetic.main.fragment_about_step4.*
import org.jetbrains.anko.support.v4.email
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

class AboutStep4Fragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_about_step4, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSendMail.setOnClickListener { email( getString(R.string.mail_peter), getString(R.string.contact_about_blumenau_social)) }

        //ivLinkedinPeter.setOnClickListener { openLinkedin() }
    }

    //TODO Get my ID in linkedn page.
    fun openLinkedin() {
        val linkedId = getString(R.string.id_linkedn)
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@$linkedId"))
        val packageManager = context?.packageManager
        val list = packageManager?.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list != null && list.isEmpty()) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("www.linkedin.com/in/$linkedId"))
        }
        startActivity(intent)
    }
    
}