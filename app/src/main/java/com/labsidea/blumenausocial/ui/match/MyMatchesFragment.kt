package com.labsidea.blumenausocial.ui.match

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.di.component.DaggerFragmentComponent
import com.labsidea.blumenausocial.di.module.FragmentModule
import com.labsidea.blumenausocial.ui.institution.detail.InstitutionDetailActivity
import com.labsidea.blumenausocial.ui.match.filter.MatchFiltersActivity
import kotlinx.android.synthetic.main.fragment_my_matches.*
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.support.v4.startActivity
import javax.inject.Inject

class MyMatchesFragment: Fragment(), MyMatchesContract.View{

    @Inject
    lateinit var presenter: MyMatchesContract.Presenter

    companion object {
        const val TAG: String = "MyMatchesFragment"
    }

    fun newInstance() = MyMatchesFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_my_matches, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependency()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter attach this

        if (vpInstitutions?.adapter == null)
            presenter.makeMatchInstitutions(context!!)

        tvStartMatch.setOnClickListener { startActivity<MatchFiltersActivity>() }

        btnFilter.setOnClickListener { startActivity<MatchFiltersActivity>() }

    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build()

        listComponent.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.unsubscribe()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK)
            presenter.makeMatchInstitutions(context!!)
    }

    override fun onMatchReady(adapter: MyMatchesPagerAdapter) {
        if (contentBar?.visibility == View.GONE) {
            contentBar?.visibility = View.VISIBLE
            llBody?.visibility = View.VISIBLE
            tvStartMatch?.visibility = View.GONE
        }

        vpInstitutions?.adapter = adapter
        setDonationsAdapter(0)

        vpInstitutions?.onPageChangeListener { onPageSelected { setDonationsAdapter(it) } }

        indicator?.setViewPager(vpInstitutions)
    }

    override fun onNeedMakeMatch() {
        tvStartMatch?.visibility = View.VISIBLE
    }

    private fun setDonationsAdapter(pageIndex: Int){
        rvDonations?.adapter = presenter getDonationsAdapter pageIndex

        if (rvDonations?.layoutManager == null)
            rvDonations?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        tvNeed?.visibility = View.VISIBLE
    }

    override fun onClickInstitution(id: Int){
        startActivity<InstitutionDetailActivity>("institution_id_selected" to id)
        activity?.overridePendingTransition(R.anim.slide_bottom_to_top, R.anim.fade_out_long)
    }


}