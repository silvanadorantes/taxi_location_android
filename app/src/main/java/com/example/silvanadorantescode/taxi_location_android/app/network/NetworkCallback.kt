package com.example.silvanadorantescode.taxi_location_android.app.network

import android.util.Log
import com.example.silvanadorantescode.taxi_location_android.app.network.data.Commons.ErrorResponse
import com.example.silvanadorantescode.taxi_location_android.app.network.interceptor.exception.NoConnectivityException
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by SilvanaDorantes on 17/03/20.
 */
abstract class NetworkCallback<T> : Callback<T>  {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            if (response.code() == 200) {
                Log.d(TAG, response.body().toString())
                onRequestSuccess(response.body() as T)
            }
        } else {
            if (response.code() == 400) {
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter<ErrorResponse>(ErrorResponse::class.java!!)
                val errorResponse = jsonAdapter.fromJson(response.errorBody()!!.string())
                onRequestFail(errorResponse!!.error)
            } else {
                onRequestFail(ERROR_CONNECTION)
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {

        if (t is NoConnectivityException) {
            onRequestFail(NO_HAVE_INTERNET)
        } else {
            onRequestFail(ERROR_CONNECTION)
        }
    }

    abstract fun onRequestSuccess(response: T)

    abstract fun onRequestFail(errorMessage: String)

    abstract fun onRequestFail(errorCode: Int)

    companion object {

        private val TAG = NetworkCallback::class.java.simpleName
        val ERROR_CONNECTION = 1001
        val NO_HAVE_INTERNET = 1002
    }



}