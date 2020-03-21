package com.example.silvanadorantescode.taxi_location_android.app.network.interceptor.exception

import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.util.Commons
import java.io.IOException


/**
 * Created by SilvanaDorantes on 17/03/20.
 */

class NoConnectivityException: IOException() {
    override val message
        get() = Commons.getString(R.string.connectivity_exception)

}