package com.example.silvanadorantescode.taxi_location_android.util.base

import androidx.lifecycle.ViewModel
import com.example.silvanadorantescode.taxi_location_android.app.App
import com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel.PlacemarksViewModel

/**
 * Created by SilvanaDorantes on 26/03/20.
 */

abstract class BaseViewModel: ViewModel() {
    private val injector = App.get().getInjector()

    init {
        inject()

    }

    private fun inject(){
        when (this) {
            is PlacemarksViewModel -> injector.inject(this)

        }
    }

}