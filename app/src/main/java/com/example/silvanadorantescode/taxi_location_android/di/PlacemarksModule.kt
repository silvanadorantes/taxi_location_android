package com.example.silvanadorantescode.taxi_location_android.di

import com.example.silvanadorantescode.taxi_location_android.app.network.api.PlacemarksApi
import com.example.silvanadorantescode.taxi_location_android.domain.usecase.PlacemarksRespositoryImpl
import com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel.PlacemarksViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by SilvanaDorantes on 17/03/20.
 */

@Module
class PlacemarksModule {


    @Provides
    @PlacemarksScope
    fun provideRespository(placemarksApi: PlacemarksApi): PlacemarksRespositoryImpl{
        return PlacemarksRespositoryImpl(placemarksApi)
    }

    @Provides
    @PlacemarksScope
    fun provideViewModel(placemarksRespositoryImpl: PlacemarksRespositoryImpl): PlacemarksViewModel{
        return PlacemarksViewModel(placemarksRespositoryImpl)
    }
}