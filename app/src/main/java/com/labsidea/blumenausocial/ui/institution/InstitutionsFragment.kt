/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.di.component.DaggerFragmentComponent
import com.labsidea.blumenausocial.di.module.FragmentModule
import kotlinx.android.synthetic.main.fragment_institutions.*
import javax.inject.Inject
import com.labsidea.blumenausocial.ui.institution.detail.InstitutionDetailActivity
import com.labsidea.blumenausocial.ui.institution.filter.InstitutionFilterActivity
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult


class InstitutionsFragment: Fragment(), InstitutionsContract.View{

    companion object {
        const val TAG: String = "InstitutionsFragment"
    }

    @Inject lateinit var presenter: InstitutionsContract.Presenter

    fun newInstance() = InstitutionsFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_institutions, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        injectDependency()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter attach this
        presenter.subscribe()
        initView()

        tbFilter.setOnClickListener { startActivityForResult<InstitutionFilterActivity>(1, "currentFilters" to presenter.filters()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.unsubscribe()
    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build()

        listComponent.inject(this)
    }

    private fun initView() {
        presenter.loadAdditionalData(context!!) { institution ->
            if (institution != null) {
                startActivity<InstitutionDetailActivity>("institution_id_selected" to institution.id)
                activity?.overridePendingTransition(R.anim.slide_bottom_to_top, R.anim.fade_out_long)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1){
            if (data != null && data.hasExtra("items_filter")){
                this.presenter.filterOrganizations(data.getParcelableArrayListExtra("items_filter"))
            }
        }
    }


    override fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorMessage(error: String) {
        showProgress(false)
        alert(error, getString(R.string.error_loading)).show()
    }

    override fun loadDataSuccess(adapter: InstitutionsAdapter) {

        rvInstitutions.visibility = View.VISIBLE
        rvInstitutions?.adapter = adapter

        if (rvInstitutions?.layoutManager == null)
            rvInstitutions?.layoutManager = LinearLayoutManager(context)
    }
}