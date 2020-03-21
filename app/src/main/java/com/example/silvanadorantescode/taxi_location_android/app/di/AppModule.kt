package com.example.silvanadorantescode.taxi_location_android.app.di

import android.content.Context
import android.content.res.Resources
import com.example.silvanadorantescode.taxi_location_android.app.App
import dagger.Module
import dagger.Provides

/**
 * Created by SilvanaDorantes on 17/03/20.
 */

@Module
class AppModule(val app: App) {
    @Provides
    @AppScope
    fun provideApp(): App {
        return app
    }

    @Provides
    @AppScope
    fun provideResources(): Resources {
        return app.getResources()
    }

    @Provides
    @AppScope
    fun provideApplicationContext(): Context {
        return app
    }

    @Provides
    @AppScope
    fun provideAppComponent(appComponent: AppComponent): AppComponent {
        return appComponent
    }
}