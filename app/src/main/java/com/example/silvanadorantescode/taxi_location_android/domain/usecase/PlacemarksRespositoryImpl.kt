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
    private var success = MutableLiveData<Boolean>()
    private var loading = MutableLiveData<Boolean>()
    private var error = MutableLiveData<Boolean>()
    private var errorFail = MutableLiveData<Boolean>()

    //Subject MutableLiveData
    //Observers List Coupon
    //Change List Coupon - MutableLiveData
    //Observe

    fun getListPlacemarks(): MutableLiveData<List<PlacemarksListItem>> {
        return placemarksList
    }

    fun getListPlacemarksSuccess(): MutableLiveData<Boolean>{
        return success
    }

    fun getListPlacemarksLoading(): MutableLiveData<Boolean>{
        return loading
    }

    fun getListPlacemarksErrorMessage(): MutableLiveData<Boolean>{
        return error
    }
    fun getListPlacemarksErrorCode(): MutableLiveData<Boolean>{
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
                success.value = true
                error.value = false
                errorFail.value = false
                loading.value = false
            }

            override fun onRequestFail(errorMessage: String) {
                Log.d(TAG, "FailResponse")
                success.value = false
                loading.value = false
                error.value = true
                errorFail.value = false

            }
            override fun onRequestFail(errorCode: Int) {
                Log.d(TAG, "FailResponse")
                if (errorCode == NetworkCallback.NO_HAVE_INTERNET){
                    Log.d(TAG,"NO HAVE INTERNET")
                    success.value = false
                    loading.value = false
                    error.value = false
                    errorFail.value = true

                }


                if (errorCode == NetworkCallback.ERROR_CONNECTION){
                    Log.d(TAG,"Error CONNECTION")
                    success.value = false
                    loading.value = false
                    error.value = false
                    errorFail.value = true


                }


            }
        })
    }


}