package com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by SilvanaDorantes on 17/03/20.
 */

data class PlacemarksListItem(var address: String?, var coordinates: List<Double?>?, var engineType: String?, var exterior: String?, var fuel: Int?, var interior: String?, var name: String?, var vin: String?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        TODO("coordinates"),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(engineType)
        parcel.writeString(exterior)
        parcel.writeValue(fuel)
        parcel.writeString(interior)
        parcel.writeString(name)
        parcel.writeString(vin)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlacemarksListItem> {
        override fun createFromParcel(parcel: Parcel): PlacemarksListItem {
            return PlacemarksListItem(parcel)
        }

        override fun newArray(size: Int): Array<PlacemarksListItem?> {
            return arrayOfNulls(size)
        }
    }
}