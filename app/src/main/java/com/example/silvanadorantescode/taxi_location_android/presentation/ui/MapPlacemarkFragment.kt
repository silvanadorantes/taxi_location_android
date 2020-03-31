package com.example.silvanadorantescode.taxi_location_android.presentation.ui

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem

import com.example.silvanadorantescode.taxi_location_android.databinding.FragmentMapPlacemarkBinding
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import javax.inject.Inject


/**
 * Created by SilvanaDorantes on 20/03/20.
 */

class MapPlacemarkFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMarkerDragListener,
    GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnIndoorStateChangeListener,
    GoogleMap.OnMapLongClickListener{

    private val args: MapPlacemarkFragmentArgs by navArgs()

    lateinit var placemarksListItem: PlacemarksListItem


    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var latLng: LatLng? = null
    var isGPS = false
    private lateinit var mMap: GoogleMap
    private lateinit var mMarker: Marker

    //private var mMap: GoogleMap? = null
    //private var mMarker: Marker? = null
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

        mapPlacemarkBinding.taxiLocationToolbar.iconLeft.setOnClickListener {
            findNavController().navigate(R.id.action_to_mapdetailplaceamrks_listplacemarks_fragment)
        }




        mapPlacemarkBinding.fab.setOnClickListener {
            openPlacemarksGeo(args.placemarksItem!!)
        }





        return mapPlacemarkBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "PlacemarksItem args")
        Log.d(TAG, "name" + " " + args.placemarksItem?.name)
        Log.d(TAG, "address" + " " + args.placemarksItem?.address)
        Log.d(TAG, "lat" + " "+ args.placemarksItem?.coordinates?.get(1).toString())
        Log.d(TAG, "lon" + " " + args.placemarksItem?.coordinates?.get(0).toString())
        placemarksListItem = args.placemarksItem as PlacemarksListItem
        Log.d(TAG, "PlacemarksItem")
        Log.d(TAG, "name" + " " +placemarksListItem?.name)
        Log.d(TAG, "address" + " " + placemarksListItem?.address)
        Log.d(TAG, "lat" + " "+ placemarksListItem?.coordinates?.get(1).toString())
        Log.d(TAG, "lon" + " " + placemarksListItem?.coordinates?.get(0).toString())




        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)





    }



    fun  openPlacemarksGeo( placemarksListItem: PlacemarksListItem){
        dialog = Dialog(requireActivity())
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_placemarks)
        dialog!!.window!!.setWindowAnimations(R.style.Theme_AppCompat_Light_Dialog)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val inflater = requireActivity().layoutInflater
        val v = inflater.inflate(R.layout.dialog_placemarks, null)

        val tvName = v.findViewById<View>(R.id.tvName) as TextView
        val tvAddress = v.findViewById<View>(R.id.tvAddress) as TextView
        val longitude = v.findViewById<View>(R.id.longitud) as TextView
        val latitud = v.findViewById<View>(R.id.latitud) as TextView
        val tvengineType = v.findViewById<View>(R.id.tvengineType) as TextView
        val tvFuel = v.findViewById<View>(R.id.tvfuel) as TextView
        val tvExterior = v.findViewById<View>(R.id.tvexterior) as TextView
        val tvInterior = v.findViewById<View>(R.id.tvinterior) as TextView
        val tvVin = v.findViewById<View>(R.id.tvvin) as TextView

        tvName.text = placemarksListItem.name
        tvAddress.text = placemarksListItem.address
        latitud.text = placemarksListItem.coordinates?.get(1).toString()
        longitude.text = placemarksListItem.coordinates?.get(0).toString()
        tvengineType.text = placemarksListItem.engineType
        tvFuel.text = placemarksListItem.fuel.toString()
        tvExterior.text = placemarksListItem.exterior
        tvInterior.text = placemarksListItem.interior
        tvVin.text = placemarksListItem.vin


        dialog!!.setContentView(v)
        dialog!!.show()





    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this!!.requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this!!.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

    }

    fun getLocation(p0: GoogleMap?) {
        Log.d(TAG, "MAPA PlacemarksItem MAPA")
        Log.d(TAG, "name" + " " +placemarksListItem!!.name)
        Log.d(TAG, "address" + " " + placemarksListItem!!.address)
        Log.d(TAG, "lat" + " "+ placemarksListItem!!.coordinates!!.get(1).toString())
        Log.d(TAG, "lon" + " " + placemarksListItem!!.coordinates!!.get(0).toString())

        if (placemarksListItem.coordinates!!.get(0) != null && placemarksListItem.coordinates!!.get(1) != null){
            mMap = p0!!
            val zoom: Float
            val lat: Double = placemarksListItem.coordinates!!.get(1)!!
            val lon: Double = placemarksListItem.coordinates!!.get(0)!!


            Log.d(TAG, "MAPA")
            Log.d(TAG, "latitud" + " "+ placemarksListItem!!.coordinates!!.get(1).toString())
            Log.d(TAG, "longitud" + " " + placemarksListItem!!.coordinates!!.get(0).toString())
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


            mMarker = p0.addMarker(MarkerOptions().position(newLatLng).title(placemarksListItem.name).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_radius_black_36dp)).snippet(placemarksListItem.address).alpha(0.8f))
            zoom = 17.0f

            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, zoom))

            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), zoom))
            mMap!!.animateCamera(CameraUpdateFactory.zoomIn())

            val cameraPosition: CameraPosition = CameraPosition.builder()
                .target(newLatLng)
                .zoom(17.0f)
                .bearing(0f)
                .tilt(30f)
                .build()

            mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


            mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


            mMap!!.setOnMarkerClickListener(this)
            mMap!!.setOnMarkerDragListener(this)
            mMap!!.setOnInfoWindowClickListener(this)



        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        setUpMap()
        getLocation(p0)


    }

    override fun onMarkerClick(p0: Marker?): Boolean {


        return  false


    }

    override fun onMarkerDragEnd(p0: Marker?) {


    }

    override fun onMarkerDragStart(p0: Marker?) {

    }

    override fun onMarkerDrag(p0: Marker?) {

    }

    override fun onInfoWindowClick(p0: Marker?) {

    }

    override fun onIndoorBuildingFocused() {

    }

    override fun onIndoorLevelActivated(p0: IndoorBuilding?) {

    }

    override fun onMapLongClick(p0: LatLng?) {

    }
}

