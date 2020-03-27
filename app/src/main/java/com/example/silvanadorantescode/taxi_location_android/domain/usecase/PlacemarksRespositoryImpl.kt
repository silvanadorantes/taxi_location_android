package com.example.silvanadorantescode.taxi_location_android.domain.usecase

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.di.ViewModelInjector
import com.example.silvanadorantescode.taxi_location_android.app.network.NetworkCallback
import com.example.silvanadorantescode.taxi_location_android.app.network.api.PlacemarksApi
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListResponse
import com.example.silvanadorantescode.taxi_location_android.di.PlacemarksScope
import com.example.silvanadorantescode.taxi_location_android.util.Commons
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton
/**
 * Created by SilvanaDorantes on 19/03/20.
 */


class PlacemarksRespositoryImpl (var placemarksApi: PlacemarksApi){

    val TAG = PlacemarksRespositoryImpl::class.java.simpleName
    lateinit var context: Context
    private var placemarksList = MutableLiveData<List<PlacemarksListItem>>()

    //Subject MutableLiveData
    //Observers List Coupon
    //Change List Coupon - MutableLiveData
    //Observe

    fun getListPlacemarks(): MutableLiveData<List<PlacemarksListItem>> {
        return placemarksList
    }

    fun callListPlacemarksAPI() {
        //var listPlacemarks: ArrayList<PlacemarksListItem>? = ArrayList<PlacemarksListItem>()
        placemarksApi.getList().enqueue(object : NetworkCallback<PlacemarksListResponse>(){
            override fun onRequestSuccess(response: PlacemarksListResponse) {
                Log.d(TAG, "ListPlacemarkOK")

                //listPlacemarks?.addAll(response.placemarks)
                placemarksList.value = response.placemarks
            }

            override fun onRequestFail(errorMessage: String) {
                Log.d(TAG, "FailResponse")
                Commons.makeToast(errorMessage, context)
            }
            override fun onRequestFail(errorCode: Int) {
                Log.d(TAG, "FailResponse")
                if (errorCode == NetworkCallback.NO_HAVE_INTERNET)
                    Commons.makeToast(Commons.getString(R.string.no_internet), context)

                if (errorCode == NetworkCallback.ERROR_CONNECTION)
                    Commons.makeToast(Commons.getString(R.string.network_error), context)

            }
        })
    }


}