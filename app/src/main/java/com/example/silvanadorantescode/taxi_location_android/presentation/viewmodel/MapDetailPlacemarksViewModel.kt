package com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.util.base.BaseViewModel

/**
 * Created by SilvanaDorantes on 19/03/20.
 */
class MapDetailPlacemarksViewModel: BaseViewModel() {
    private val TAG = MapDetailPlacemarksViewModel::class.java.simpleName
    lateinit var placemark: PlacemarksListItem
    var selected: MutableLiveData<PlacemarksListItem> = MutableLiveData<PlacemarksListItem>()


}