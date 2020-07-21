/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.di.component

import com.labsidea.blumenausocial.ui.main.MainActivity
import com.labsidea.blumenausocial.di.module.ActivityModule
import com.labsidea.blumenausocial.ui.base.BaseContract
import com.labsidea.blumenausocial.ui.institution.detail.InstitutionDetailActivity
import com.labsidea.blumenausocial.ui.institution.filter.InstitutionFilterActivity
import com.labsidea.blumenausocial.ui.match.filter.MatchFiltersActivity
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: InstitutionDetailActivity)

    fun inject(activity: InstitutionFilterActivity)

    fun inject(activity: MatchFiltersActivity)

}