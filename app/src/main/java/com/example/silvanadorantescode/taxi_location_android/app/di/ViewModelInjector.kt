package com.example.silvanadorantescode.taxi_location_android.app.di


import com.example.silvanadorantescode.taxi_location_android.app.network.di.RepositoryModule
import com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel.PlacemarksViewModel
import dagger.Component


/**
 * Created by SilvanaDorantes on 25/03/20.
 */

@ViewModelScope
@Component(dependencies = [AppComponent::class], modules = [RepositoryModule::class])
interface ViewModelInjector {

    fun inject(placemarksViewModel: PlacemarksViewModel)


    @Component.Builder
    interface Builder{
        fun build(): ViewModelInjector
        fun appComponent(appComponent: AppComponent): Builder
    }




}