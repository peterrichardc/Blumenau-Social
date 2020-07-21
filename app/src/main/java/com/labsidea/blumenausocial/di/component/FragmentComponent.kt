/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.di.component

import com.labsidea.blumenausocial.di.module.FragmentModule
import com.labsidea.blumenausocial.ui.institution.InstitutionsFragment
import com.labsidea.blumenausocial.ui.match.MyMatchesFragment
import com.labsidea.blumenausocial.ui.match.filter.steps.StepFourFragment
import com.labsidea.blumenausocial.ui.match.filter.steps.StepOneFragment
import com.labsidea.blumenausocial.ui.match.filter.steps.StepThreeFragment
import com.labsidea.blumenausocial.ui.match.filter.steps.StepTwoFragment
import dagger.Component

@Component(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(listFragment: InstitutionsFragment)


    fun inject(fragment: StepTwoFragment)
    fun inject(fragment: StepThreeFragment)
    fun inject(fragment: StepFourFragment)

    fun inject(fragment: MyMatchesFragment)
}