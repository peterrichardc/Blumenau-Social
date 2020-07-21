package com.labsidea.blumenausocial.models

import android.os.Parcel
import android.os.Parcelable

class ItemAdapter (var id: Int, var description: String?, var selected: Int = 0, var icon: String? = ""): Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(description)
        parcel.writeInt(selected)
        parcel.writeString(icon)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ItemAdapter> {

        override fun createFromParcel(parcel: Parcel) = ItemAdapter(parcel)

        override fun newArray(size: Int): Array<ItemAdapter?> = arrayOfNulls(size)
    }
}

enum class FiltersType { NEIGHBORHOODS, CAUSES, DONATIONS, VOLUNTEERS, DAYS_OF_WEEK, TIMES_OF_DAY}

class InstitutionsFilterHeader(val resourceId: Int, var title: String, var subtitle: String, var type: FiltersType)

class ItemsSelected (val id: Int, var type: FiltersType): Parcelable{

    constructor(parcel: Parcel) : this(parcel.readInt(), FiltersType.values()[parcel.readInt()])

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(type.ordinal)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ItemsSelected> {

        override fun createFromParcel(parcel: Parcel) = ItemsSelected(parcel)

        override fun newArray(size: Int): Array<ItemsSelected?> = arrayOfNulls(size)
    }
}