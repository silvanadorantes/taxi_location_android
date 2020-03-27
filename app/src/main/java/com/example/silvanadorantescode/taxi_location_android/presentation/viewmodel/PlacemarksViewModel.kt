package com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.domain.usecase.PlacemarkRespository
import com.example.silvanadorantescode.taxi_location_android.domain.usecase.PlacemarksRespositoryImpl
import com.example.silvanadorantescode.taxi_location_android.presentation.adapter.PlacemarksAdapter
import javax.inject.Inject

/**
 * Created by SilvanaDorantes on 19/03/20.
 */


class PlacemarksViewModel(): ViewModel() {

    private val TAG = PlacemarksViewModel::class.java.simpleName
    private var recyclerPlacemarksAdapter:PlacemarksAdapter? = null
    var selected: MutableLiveData<PlacemarksListItem> = MutableLiveData<PlacemarksListItem>()
    private var placemarksRespositoryImpl: PlacemarksRespositoryImpl? = null


    fun callPlacemarks(){
        placemarksRespositoryImpl?.callListPlacemarksAPI()
    }

    fun getPlacemarks(): MutableLiveData<List<PlacemarksListItem>>?{
        return placemarksRespositoryImpl?.getListPlacemarks()
    }

    fun setListPlacemarksInRecyclerAdapter(listPlacemarks: List<PlacemarksListItem>){
        recyclerPlacemarksAdapter?.setPlacemarksList(listPlacemarks)
        Log.d(TAG, "listPlacemarksSize" +" "+ listPlacemarks.size)
        Log.d(TAG, "listPlacemarks" +" "+ listPlacemarks)
        recyclerPlacemarksAdapter?.notifyDataSetChanged()
    }

    fun getRecyclerPlacemarksAdapter(): PlacemarksAdapter?{
        recyclerPlacemarksAdapter = PlacemarksAdapter(this, R.id.item_list_placemarks)
        return recyclerPlacemarksAdapter
    }

    fun getPlacemarksAt(position: Int):PlacemarksListItem?{
        var listPlacemarks: List<PlacemarksListItem>? = placemarksRespositoryImpl?.getListPlacemarks()?.value
        return listPlacemarks?.get(position)
    }

    fun getPlacemarksSelected(): MutableLiveData<PlacemarksListItem>{
        return selected
    }

    fun onItemClick(index: Int){
        val placemarksListItem = getPlacemarksAt(index)
        selected.value = placemarksListItem
    }


}