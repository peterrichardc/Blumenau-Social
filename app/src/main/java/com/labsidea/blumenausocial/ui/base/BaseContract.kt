/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.base

//Base contract, to be implemented by every contract.
interface BaseContract{

    interface Presenter<in T> {
        fun subscribe()
        fun unsubscribe()
        infix fun attach(view: T)
    }

    interface View

}