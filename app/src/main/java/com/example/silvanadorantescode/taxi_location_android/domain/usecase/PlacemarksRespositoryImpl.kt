package com.example.silvanadorantescode.taxi_location_android.domain.usecase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.network.NetworkCallback
import com.example.silvanadorantescode.taxi_location_android.app.network.api.PlacemarksApi
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListResponse
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton
/**
 * Created by SilvanaDorantes on 19/03/20.
 */
@Singleton
class PlacemarksRespositoryImpl @Inject constructor(var placemarksApi: PlacemarksApi): PlacemarkRespository{

    val TAG = PlacemarksRespositoryImpl::class.java.simpleName
    private var placemarksList = MutableLiveData<List<PlacemarksListItem>>()
    //Subject MutableLiveData
    //Observers List Coupon
    //Change List Coupon - MutableLiveData
    //Observe


    override fun getListPlacemarks(): MutableLiveData<List<PlacemarksListItem>> {
        return placemarksList
    }

    override fun callListPlacemarksAPI(resultListener: PlacemarkRespository.ResultListener) {
        placemarksApi.getList().enqueue(object : NetworkCallback<PlacemarksListResponse>(){

            override fun onRequestSuccess(response: PlacemarksListResponse) {
                Log.d(TAG, "ListPlacemarkOK")
                resultListener.onListPlacemarksSuccess(response.placemarks!!)
                placemarksList.value = response.placemarks

            }

            override fun onRequestFail(errorMessage: String) {
                Log.d(TAG, "FailResponse")
                resultListener.onFailure(errorMessage)

            }

            override fun onRequestFail(errorCode: Int) {
                Log.d(TAG, "FailResponse")
                if (errorCode == NetworkCallback.NO_HAVE_INTERNET)
                    resultListener.onFailure(R.string.no_internet)

                if (errorCode == NetworkCallback.ERROR_CONNECTION)
                    resultListener.onFailure(R.string.network_error)


            }
        })

    }
}