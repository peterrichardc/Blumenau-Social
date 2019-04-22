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

        if (BuildConfig.DEBUG) {
            // Maybe implement etc.
        }

        Realm.init(this)
    }

    fun setup() {
        component = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}