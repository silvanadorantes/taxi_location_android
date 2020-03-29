package com.example.silvanadorantescode.taxi_location_android.app.network.di

import com.example.silvanadorantescode.taxi_location_android.app.di.AppScope
import com.example.silvanadorantescode.taxi_location_android.app.di.ViewModelScope
import com.example.silvanadorantescode.taxi_location_android.app.network.api.PlacemarksApi
import com.example.silvanadorantescode.taxi_location_android.domain.usecase.PlacemarksRespositoryImpl
import dagger.Module
import dagger.Provides

/**
 * Created by SilvanaDorantes on 25/03/20.
 */

@Module
class RepositoryModule {

    @Provides
    @ViewModelScope
    fun providePlacemarksRepository(placemarksApi: PlacemarksApi): PlacemarksRespositoryImpl{
        return PlacemarksRespositoryImpl(placemarksApi)
    }
}