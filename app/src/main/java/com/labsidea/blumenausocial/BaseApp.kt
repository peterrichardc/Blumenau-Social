/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial

import android.app.Application
import com.labsidea.blumenausocial.di.component.ApplicationComponent
import com.labsidea.blumenausocial.di.component.DaggerApplicationComponent
import com.labsidea.blumenausocial.di.module.ApplicationModule
import io.realm.Realm

class BaseApp: Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()
    }

    fun setup() {
        //TODO Remove .applicationModule()
        component = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        component.inject(this)

        //Realm initialization.
        Realm.init(this)
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}