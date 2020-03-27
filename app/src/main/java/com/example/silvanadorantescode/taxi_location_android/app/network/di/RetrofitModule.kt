package com.example.silvanadorantescode.taxi_location_android.app.network.di

import android.content.res.Resources
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.di.AppScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * Created by SilvanaDorantes on 17/03/20.
 */

@Module(includes = [NetworkModule::class])
class RetrofitModule {

    @Provides
    @AppScope
    fun provideBaseUrl(resources: Resources): String {
        return resources.getString(R.string.api_url)
    }


    @Provides
    @AppScope
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @AppScope
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        gson: Gson,
                        url: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .baseUrl(url)
            .build()
    }

}