package com.example.silvanadorantescode.taxi_location_android.app.network.di

import com.example.silvanadorantescode.taxi_location_android.app.di.AppScope
import com.example.silvanadorantescode.taxi_location_android.app.network.api.PlacemarksApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
* Created by SilvanaDorantes on 17/03/20.
*/

@Module(includes = arrayOf(RetrofitModule::class))
class ApiModule {
    @Provides
    @AppScope
    fun providePlacemarksApi(retrofit: Retrofit): PlacemarksApi{
        return retrofit.create(PlacemarksApi::class.java)
    }
}