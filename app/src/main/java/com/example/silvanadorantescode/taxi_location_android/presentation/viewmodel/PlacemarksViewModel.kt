package com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.domain.usecase.PlacemarkRespository
import com.example.silvanadorantescode.taxi_location_android.domain.usecase.PlacemarksRespositoryImpl
import com.example.silvanadorantescode.taxi_location_android.presentation.adapter.PlacemarksAdapter
import com.example.silvanadorantescode.taxi_location_android.util.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by SilvanaDorantes on 19/03/20.
 */


class PlacemarksViewModel(): BaseViewModel() {

    private val TAG = PlacemarksViewModel::class.java.simpleName
    private var recyclerPlacemarksAdapter:PlacemarksAdapter? = null
    var selected: MutableLiveData<PlacemarksListItem> = MutableLiveData<PlacemarksListItem>()
    var placemark: MutableLiveData<PlacemarksListItem> = MutableLiveData<PlacemarksListItem>()
    var error: MutableLiveData<String> = MutableLiveData<String>()
    var errorFail: MutableLiveData<String> = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()



    @Inject
    lateinit var placemarksRespositoryImpl:PlacemarksRespositoryImpl


    fun callPlacemarks(){
        placemarksRespositoryImpl.callListPlacemarksAPI()
    }

    fun getPlacemarks(): MutableLiveData<List<PlacemarksListItem>>{
        return placemarksRespositoryImpl.getListPlacemarks()
    }





    fun getListPlacemarksLoading(): MutableLiveData<Boolean>{
        return placemarksRespositoryImpl.getListPlacemarksLoading()
    }

    fun getListPlacemarksErrorMessage(): MutableLiveData<String>{
        return placemarksRespositoryImpl.getListPlacemarksErrorMessage()
    }
    fun getListPlacemarksErrorCode(): MutableLiveData<String>{
        return placemarksRespositoryImpl.getListPlacemarksErrorCode()
    }


    fun getPlacemarksAt(position: Int):PlacemarksListItem?{
        var listPlacemarks: List<PlacemarksListItem>? = placemarksRespositoryImpl.getListPlacemarks().value
        return listPlacemarks?.get(position)
    }

    fun getPlacemarksItem(): MutableLiveData<PlacemarksListItem>{
        return placemark
    }


    fun getPlacemarksSelected(): MutableLiveData<PlacemarksListItem>{
        return selected
    }

    fun onItemClick(index: Int){
        val placemarksListItem = getPlacemarksAt(index)
        selected.value = placemarksListItem
    }



}