/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.models

import com.google.gson.annotations.SerializedName

class OrganizationAdditionalInformationList(@SerializedName("neighborhoods") val neighborhoods: List<Neighborhood>?, @SerializedName("areas") val causes: List<Causes>?, @SerializedName("donations") val donations: List<Donations>?, @SerializedName("volunteers") val volunteers: List<Volunteers>?)