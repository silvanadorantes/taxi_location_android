package com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.domain.usecase.PlacemarkRespository
import com.example.silvanadorantescode.taxi_location_android.domain.usecase.PlacemarksRespositoryImpl
import javax.inject.Inject

/**
 * Created by SilvanaDorantes on 19/03/20.
 */
class PlacemarksViewModel @Inject constructor(var placemarkRespository: PlacemarksRespositoryImpl): ViewModel() {

    val TAG = PlacemarksViewModel::class.java.simpleName
    var repository: PlacemarksRespositoryImpl? = null




}