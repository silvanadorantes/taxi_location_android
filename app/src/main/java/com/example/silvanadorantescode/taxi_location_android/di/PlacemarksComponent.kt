package com.example.silvanadorantescode.taxi_location_android.di

import com.example.silvanadorantescode.taxi_location_android.app.di.AppComponent
import com.example.silvanadorantescode.taxi_location_android.presentation.ui.ListPlacemarksFragment
import dagger.Component

/**
 * Created by SilvanaDorantes on 17/03/20.
 */
@PlacemarksScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(PlacemarksModule::class))
interface PlacemarksComponent {
    fun inject(view: ListPlacemarksFragment)

}