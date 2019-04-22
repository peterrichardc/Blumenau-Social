/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.di.component

import com.labsidea.blumenausocial.di.module.FragmentModule
import com.labsidea.blumenausocial.ui.institution.InstitutionsFragment
import dagger.Component

@Component(modules = [FragmentModule::class])
interface FragmentComponent {

   // fun inject(aboutFragment: AboutFragment)

    fun inject(listFragment: InstitutionsFragment)

}