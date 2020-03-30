package com.example.silvanadorantescode.taxi_location_android.presentation.binding

import android.os.Bundle
import androidx.databinding.BindingAdapter
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


/**
 * Created by SilvanaDorantes on 21/03/20.
 */

@BindingAdapter("initMap")
fun initMap(mapView: MapView?, latLng: LatLng?): Unit {
    if (mapView != null) {
        mapView.onCreate(Bundle())
        mapView.getMapAsync(OnMapReadyCallback { googleMap -> // Add a marker
            googleMap.addMarker(MarkerOptions().position(latLng!!).title(""))
        })
    }
}