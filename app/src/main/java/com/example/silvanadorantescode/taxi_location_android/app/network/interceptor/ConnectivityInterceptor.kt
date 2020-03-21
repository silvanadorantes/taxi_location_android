package com.example.silvanadorantescode.taxi_location_android.app.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.silvanadorantescode.taxi_location_android.app.network.interceptor.exception.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by SilvanaDorantes on 17/03/20.
 */

class ConnectivityInterceptor(var context: Context) : Interceptor {

    companion object {
        @JvmStatic
        fun isOnline(c: Context): Boolean {
            val connectivityManager: ConnectivityManager = c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager.getActiveNetworkInfo() == null)
                return false
            val netInfo: NetworkInfo = connectivityManager.getActiveNetworkInfo();
            return (netInfo.isConnected())
        }
    }

    @Throws(IOException::class)
    override
    fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline(context)) {
            throw NoConnectivityException()
        }

        val builder: Request.Builder
        builder = chain.request().newBuilder()
        return chain.proceed(builder.build());
    }


}