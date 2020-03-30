package com.example.silvanadorantescode.taxi_location_android.presentation.ui

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.silvanadorantescode.taxi_location_android.R

import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.databinding.FragmentMapPlacemarkBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


/**
 * Created by SilvanaDorantes on 20/03/20.
 */

class MapPlacemarkFragment : Fragment(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    com.google.android.gms.location.LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapLongClickListener{

    private val args: MapPlacemarkFragmentArgs by navArgs()

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var latLng: LatLng? = null
    var isGPS = false
    private var mMap: GoogleMap? = null
    private var mMarker: Marker? = null
    internal var myMarker: Marker? = null
    internal var prevMarker: Marker? = null
    internal var iPoints: Marker? = null
    lateinit var marker: Array<Marker?>
    private var dialog: Dialog? = null
    protected var REQUEST_CHECK_SETTINGS  = 0x1
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocation: Location? = null
    private var mLocationManager: LocationManager? = null
    private var mLastLocation: Location? = null
    private var mLocationRequest: LocationRequest? = null
    private var  mLocationSettingsRequest: LocationSettingsRequest? = null;
    private val listener: com.google.android.gms.location.LocationListener? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

    private var locationManager: LocationManager? = null
    private val isLocationEnabled: Boolean
        get() {
            locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER)
        }


    companion object{
        val TAG = MapPlacemarkFragment::class.java.simpleName
        val LOCATION_PERMISSION_REQUEST_CODE = 1


        @JvmStatic
        fun newInstance(): MapPlacemarkFragment{
            var mapPlacemarkFragment = MapPlacemarkFragment()
            var bundle = Bundle()
            mapPlacemarkFragment.arguments = bundle
            return mapPlacemarkFragment
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val mapPlacemarkBinding = FragmentMapPlacemarkBinding.inflate(inflater, container, false)
        context ?: return mapPlacemarkBinding.root




        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        return mapPlacemarkBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this!!.requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this!!.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

    }

    fun getLocation(p0: GoogleMap) {
        Log.d(TAG, "PlacemarksItem args")
        Log.d(TAG, "name" + " " + args.placemarksItem?.name)
        Log.d(TAG, "address" + " " + args.placemarksItem?.address)
        Log.d(TAG, "lat" + " "+ args.placemarksItem?.coordinates?.get(0).toString())
        Log.d(TAG, "lon" + " " + args.placemarksItem?.coordinates?.get(1).toString())
        if (args.placemarksItem?.coordinates?.get(0) != null && args.placemarksItem?.coordinates?.get(1) != null){
            mMap = p0
            val zoom: Float
            val lat: Double = args.placemarksItem!!.coordinates!!.get(0)!!
            val lon: Double = args.placemarksItem!!.coordinates!!.get(1)!!
            val newLatLng = LatLng(lat, lon)
            setUpMap()

            if (ActivityCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap!!.isMyLocationEnabled = true
            }

            mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap!!.uiSettings.isZoomControlsEnabled = true

            mMap!!.maxZoomLevel
            mMap!!.setMinZoomPreference(2.0f)
            mMap!!.setMaxZoomPreference(42.0f)
            mMap!!.focusedBuilding


            mMarker = p0.addMarker(MarkerOptions().position(newLatLng).title(args.placemarksItem?.name).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_black_36dp)).snippet(args.placemarksItem?.address).alpha(0.8f))
            zoom = 17.0f

            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, zoom))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), zoom))
            mMap!!.animateCamera(CameraUpdateFactory.zoomIn())

            val cameraPosition: CameraPosition = CameraPosition.builder()
                .target(newLatLng)
                .zoom(17.0f)
                .bearing(90f)
                .tilt(30f)
                .build()

            mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


            mMap!!.setOnMarkerClickListener(this)
            mMap!!.setOnMarkerDragListener(this)
            mMap!!.setOnInfoWindowClickListener(this)



        }
    }




    override fun onMapReady(p0: GoogleMap?) {
        setUpMap()
        getLocation(p0!!)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragEnd(p0: Marker?) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragStart(p0: Marker?) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDrag(p0: Marker?) {
        TODO("Not yet implemented")
    }

    override fun onInfoWindowClick(p0: Marker?) {
        TODO("Not yet implemented")
    }

    override fun onMapLongClick(p0: LatLng?) {
        TODO("Not yet implemented")
    }

    override fun onConnected(p0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(p0: Location?) {
        TODO("Not yet implemented")
    }
}

