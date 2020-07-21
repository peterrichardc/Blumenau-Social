/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.di.module

import com.labsidea.blumenausocial.ui.institution.InstitutionsContract
import com.labsidea.blumenausocial.ui.institution.InstitutionsPresenter
import com.labsidea.blumenausocial.ui.match.MyMatchesContract
import com.labsidea.blumenausocial.ui.match.MyMatchesPresenter
import com.labsidea.blumenausocial.ui.match.filter.steps.StepsContract
import com.labsidea.blumenausocial.ui.match.filter.steps.StepsPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    /*
    @Provides
    fun provideAboutPresenter(): AboutContract.Presenter {
        return AboutPresenter()
    }*/

    @Provides
    fun provideInstitutionPresenter(): InstitutionsContract.Presenter {
        return InstitutionsPresenter()
    }

    @Provides
    fun provideStepsPresenter(): StepsContract.Presenter {
        return StepsPresenter()
    }

    @Provides
    fun provideMyMatchesPresenter(): MyMatchesContract.Presenter {
        return MyMatchesPresenter()
    }


}