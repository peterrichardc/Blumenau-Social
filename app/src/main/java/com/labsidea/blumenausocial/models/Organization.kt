/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Organization : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
    var subtitle: String = ""
    var phone: String = ""
    var address: String = ""
    var mail: String = ""
    var responsible: String = ""
    var working_hours: String = ""
    var days: RealmList<Int> = RealmList()
    var periods: RealmList<Int> = RealmList()
    var causes: RealmList<Int> = RealmList()
    var donation_type: RealmList<Int> = RealmList()
    var volunteer_type: RealmList<Int> = RealmList()
    var donations: RealmList<String> = RealmList()
    var volunteers: String = ""
    var about: RealmList<String> = RealmList()
    var scope : String = ""
    var neighborhood: Int = 0
    var logo: String = ""

}