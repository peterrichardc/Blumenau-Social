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
import kotlinx.android.synthetic.main.layout_developers.*
import kotlinx.android.synthetic.main.layout_social_medias.*

class AboutStep4Fragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_about_step4, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSendMail.setOnClickListener {
            val emails = "${getString(R.string.mail_bnu_social)};${getString(R.string.mail_peter)}"
            email( emails, getString(R.string.contact_about_blumenau_social))
        }

        btnFacebook.setOnClickListener { openFacebook() }

        btnInstagram.setOnClickListener { openInstagram() }

        ivLinkednPeter.setOnClickListener { openLinkedin(getString(R.string.id_linkedn)) }

        ivLinkednThiago.setOnClickListener { openLinkedin(getString(R.string.id_linkedn2)) }


    }

    private fun openLinkedin(linkedinid: String) {
        val intent =
                try {
                    activity?.packageManager?.getPackageInfo("com.linkedin.android", 0)
                    Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://$linkedinid"))
                } catch (e: Exception) {
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/$linkedinid"))
                }

        startActivity(intent)
    }

    private fun openFacebook(){
        val intent =
                try {
                    activity?.packageManager?.getPackageInfo("com.facebook.katana", 0)
                    Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/426253597411506"))
                } catch (e: Exception) {
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/BlumenauSocial"))
                }
        startActivity(intent)
    }

    private fun openInstagram(){
        val intent =
                try {
                    activity?.packageManager?.getPackageInfo("com.instagram.android", 0)
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/blumenausocial"))
                } catch (e: Exception) {
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/blumenausocial"))
                }
        startActivity(intent)
    }
    
}