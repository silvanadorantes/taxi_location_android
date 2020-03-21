package com.example.silvanadorantescode.taxi_location_android.app.di

import com.example.silvanadorantescode.taxi_location_android.app.network.api.PlacemarksApi
import com.example.silvanadorantescode.taxi_location_android.app.network.di.ApiModule
import dagger.Component


/**
 * Created by SilvanaDorantes on 17/03/20.
 */

@AppScope
@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {
    fun placemarksApi(): PlacemarksApi
}