/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.di.module

import android.app.Activity
import com.labsidea.blumenausocial.ui.institution.detail.InstitutionDetailContract
import com.labsidea.blumenausocial.ui.institution.detail.InstitutionDetailPresenter
import com.labsidea.blumenausocial.ui.institution.filter.InstitutionFilterContract
import com.labsidea.blumenausocial.ui.institution.filter.InstitutionFilterPresenter
import com.labsidea.blumenausocial.ui.main.MainContract
import com.labsidea.blumenausocial.ui.main.MainPresenter
import com.labsidea.blumenausocial.ui.match.filter.steps.StepsContract
import com.labsidea.blumenausocial.ui.match.filter.steps.StepsPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun providePresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideInstitutionDetailActivity(): InstitutionDetailContract.Presenter {
        return InstitutionDetailPresenter(activity.intent.getIntExtra("institution_id_selected", 0))
    }


    @Provides
    fun provideInstitutionFilterActivity(): InstitutionFilterContract.Presenter{
        return InstitutionFilterPresenter(activity.applicationContext, activity.intent.getParcelableArrayListExtra("currentFilters"))
    }

    /*@Provides
    fun provideMatchFiltersActivity(): StepsContract.Presenter{
        return StepsPresenter()
    }*/



}