package com.example.silvanadorantescode.taxi_location_android.app.network.api

import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by SilvanaDorantes on 17/03/20.
 */

interface PlacemarksApi {
    @GET("jobs/test")
    fun getList(): Call<PlacemarksListResponse>
}