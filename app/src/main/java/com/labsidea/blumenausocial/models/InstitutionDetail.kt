/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/
package com.labsidea.blumenausocial.models

import com.google.gson.annotations.SerializedName

class InstitutionDetail{

    @SerializedName("institution")
    val institution: Organization? = null
    var selected_causes: String = ""
    var selected_days: String = ""
    var selected_periods: String = ""

}