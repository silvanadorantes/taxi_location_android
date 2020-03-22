package com.example.silvanadorantescode.taxi_location_android.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem

/**
 * Created by SilvanaDorantes on 20/03/20.
 */

class MapPlacemarkFragment : Fragment() {

    companion object{
        val TAG = MapPlacemarkFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(placemarksListItem: PlacemarksListItem): MapPlacemarkFragment{
            var mapPlacemarkFragment = MapPlacemarkFragment()
            var bundle = Bundle()
            bundle.putParcelable("placemarksListItem", placemarksListItem)
            mapPlacemarkFragment.arguments = bundle
            return mapPlacemarkFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_placemark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_MapPlacemarksFragment_to_ListPlacemarksFragment)
        }
    }
}
