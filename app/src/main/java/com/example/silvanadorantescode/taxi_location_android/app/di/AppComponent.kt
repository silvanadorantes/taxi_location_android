package com.example.silvanadorantescode.taxi_location_android.app.di

import android.app.Application
import com.example.silvanadorantescode.taxi_location_android.app.network.api.PlacemarksApi
import com.example.silvanadorantescode.taxi_location_android.app.network.di.ApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


/**
 * Created by SilvanaDorantes on 17/03/20.
 */

@AppScope
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun placemarksApi(): PlacemarksApi
}