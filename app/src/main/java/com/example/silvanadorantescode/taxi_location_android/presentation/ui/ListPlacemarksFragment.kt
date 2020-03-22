package com.example.silvanadorantescode.taxi_location_android.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.silvanadorantescode.taxi_location_android.R

/**
 * Created by SilvanaDorantes on 20/03/20.
 */

class ListPlacemarksFragment : Fragment() {

    companion object{
        val TAG = ListPlacemarksFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): ListPlacemarksFragment{
            var listPlacemarksFragment = ListPlacemarksFragment()
            var bundle = Bundle()
            listPlacemarksFragment.arguments = bundle
            return listPlacemarksFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_placemarks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_ListPlacemarksFragment_to_MapPlacemarksFragment)
        }
    }
}
