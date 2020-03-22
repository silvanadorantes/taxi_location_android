package com.example.silvanadorantescode.taxi_location_android.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem

/**
 * Created by SilvanaDorantes on 19/03/20.
 */
interface PlacemarkRespository {
    fun getListPlacemarks(): MutableLiveData<List<PlacemarksListItem>>
    fun callListPlacemarksAPI(resultListener: ResultListener)

    interface ResultListener{
        fun onListPlacemarksSuccess(listPlacemarks: List<PlacemarksListItem>)
        fun onFailure(msg: String)
        fun onFailure(msg: Int)
    }
}