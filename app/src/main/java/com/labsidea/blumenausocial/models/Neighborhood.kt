/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Neighborhood : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var image: String = ""
}