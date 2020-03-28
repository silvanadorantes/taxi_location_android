package com.example.silvanadorantescode.taxi_location_android.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.databinding.FragmentListPlacemarksBinding
import com.example.silvanadorantescode.taxi_location_android.presentation.adapter.PlacemarksAdapter
import com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel.PlacemarksViewModel

import javax.inject.Inject

/**
 * Created by SilvanaDorantes on 20/03/20.
 */

class ListPlacemarksFragment : Fragment(){

    lateinit var listPlacemarksBinding: FragmentListPlacemarksBinding

    @Inject
    lateinit var placemarksViewModel: PlacemarksViewModel

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
        listPlacemarksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_placemarks, container, false)
        placemarksViewModel = ViewModelProviders.of(this).get(PlacemarksViewModel::class.java)
        context ?: return listPlacemarksBinding.root
        listPlacemarksBinding.model = placemarksViewModel
        listPlacemarksBinding.lifecycleOwner = this
        setUpListUpdate()




        return listPlacemarksBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }




    fun setUpListUpdate(){
        Log.d(TAG, "CallPlacemarks")
        placemarksViewModel.callPlacemarks()
        Log.d(TAG, "GetPlacemarks - ListPlacemarks")
        placemarksViewModel.getPlacemarks()?.observe(viewLifecycleOwner, Observer {
            listPlacemarks: List<PlacemarksListItem> ->
            Log.d(TAG, "listPlacemarksSize" + " " + listPlacemarks!!.size)
            Log.d(TAG, "listPlacemarks" + " " + listPlacemarks)
            placemarksViewModel.setListPlacemarksInRecyclerAdapter(listPlacemarks)
            Log.d(TAG, "listPlacemarksSize" + " " + listPlacemarks!!.size)
            Log.d(TAG, "listPlacemarks" + " " + listPlacemarks)
        })

        setupListClick()
    }

    fun setupListClick(){
        placemarksViewModel.getPlacemarksSelected().observe(viewLifecycleOwner,
            Observer {placemarksListItem: PlacemarksListItem? ->
                Log.d(TAG,"placemarks" + " " + placemarksListItem?.name)
                Log.d(TAG, "placemarksAddress" + " " + placemarksListItem?.address)
                findNavController().navigate(R.id.action_ListPlacemarksFragment_to_MapPlacemarksFragment)
            })
    }
}
