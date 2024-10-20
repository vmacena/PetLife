package com.macena.petlife.model

import android.os.Parcel
import android.os.Parcelable

data class Pet(
    val name: String,
    val birthDate: String,
    val type: String,
    val color: String,
    val size: String,
    val lastVetVisit: String,
    val lastVaccination: String,
    val lastPetShopVisit: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(birthDate)
        parcel.writeString(type)
        parcel.writeString(color)
        parcel.writeString(size)
        parcel.writeString(lastVetVisit)
        parcel.writeString(lastVaccination)
        parcel.writeString(lastPetShopVisit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pet> {
        override fun createFromParcel(parcel: Parcel): Pet {
            return Pet(parcel)
        }

        override fun newArray(size: Int): Array<Pet?> {
            return arrayOfNulls(size)
        }
    }
}