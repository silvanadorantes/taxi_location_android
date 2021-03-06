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
    private var placemarksList = MutableLiveData<List<PlacemarksListItem>>()
    private var loading = MutableLiveData<Boolean>()
    private var error = MutableLiveData<String>()
    private var errorFail = MutableLiveData<String>()

    //Subject MutableLiveData
    //Observers List Coupon
    //Change List Coupon - MutableLiveData
    //Observe

    fun getListPlacemarks(): MutableLiveData<List<PlacemarksListItem>> {
        return placemarksList
    }


    fun getListPlacemarksLoading(): MutableLiveData<Boolean>{
        return loading
    }

    fun getListPlacemarksErrorMessage(): MutableLiveData<String>{
        return error
    }
    fun getListPlacemarksErrorCode(): MutableLiveData<String>{
        return errorFail
    }






    fun callListPlacemarksAPI() {
        var listPlacemarks: ArrayList<PlacemarksListItem>? = ArrayList<PlacemarksListItem>()
        loading.value = true
        placemarksApi.getList().enqueue(object : NetworkCallback<PlacemarksListResponse>(){
            override fun onRequestSuccess(response: PlacemarksListResponse) {
                Log.d(TAG, "ListPlacemarkOK")

                listPlacemarks?.addAll(response.placemarks)
                placemarksList.value = listPlacemarks
                loading.value = false
            }

            override fun onRequestFail(errorMessage: String) {
                Log.d(TAG, "FailResponse")
                loading.value = false
                error.value = errorMessage


            }
            override fun onRequestFail(errorCode: Int) {
                Log.d(TAG, "FailResponse")
                if (errorCode == NetworkCallback.NO_HAVE_INTERNET){
                    Log.d(TAG,"NO HAVE INTERNET")
                    loading.value = false
                    errorFail.value = Commons.getString(R.string.no_internet)

                }


                if (errorCode == NetworkCallback.ERROR_CONNECTION){
                    Log.d(TAG,"Error CONNECTION")
                    loading.value = false

                    errorFail.value = Commons.getString(R.string.network_error)


                }


            }
        })
    }


}