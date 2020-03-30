package com.example.silvanadorantescode.taxi_location_android.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders

import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.databinding.FragmentListPlacemarksBinding
import com.example.silvanadorantescode.taxi_location_android.presentation.adapter.PlacemarksAdapter
import com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel.PlacemarksViewModel
import kotlinx.android.synthetic.main.fragment_list_placemarks.*


import javax.inject.Inject

/**
 * Created by SilvanaDorantes on 20/03/20.
 */

class ListPlacemarksFragment : Fragment(){



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
        placemarksViewModel = ViewModelProviders.of(this).get(PlacemarksViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val listPlacemarksBinding = FragmentListPlacemarksBinding.inflate(inflater, container, false)
        context ?: return listPlacemarksBinding.root



        val adapter = PlacemarksAdapter()
        listPlacemarksBinding.rvListPlacemarks.adapter = adapter
        Log.d(TAG, "adapter" + " " + adapter)
        subscribeUi(adapter, listPlacemarksBinding)
        adapter.notifyDataSetChanged()
        return listPlacemarksBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun subscribeUi(adapter: PlacemarksAdapter, listPlacemarksBinding: FragmentListPlacemarksBinding){
        Log.d(TAG, "CallPlacemarks")
        placemarksViewModel.callPlacemarks()
        Log.d(TAG, "GetPlacemarks - ListPlacemarks")
        placemarksViewModel.getPlacemarks().observe(viewLifecycleOwner, Observer {
            listPlacemarks: List<PlacemarksListItem> ->
            Log.d(TAG, "listPlacemarksSize" + " " + listPlacemarks!!.size)
            Log.d(TAG, "listPlacemarks" + " " + listPlacemarks)
            adapter.submitList(listPlacemarks)
            Log.d(TAG, "listPlacemarksSize" + " " + listPlacemarks!!.size)
            Log.d(TAG, "listPlacemarks" + " " + listPlacemarks)
        })

        placemarksViewModel.getListPlacemarksLoading().observe(viewLifecycleOwner, Observer {
            isLoading: Boolean ->

            isLoading.let {
                progressbar.visibility = if (it) View.VISIBLE else View.GONE

            }
        })

        placemarksViewModel.getListPlacemarksSuccess().observe(viewLifecycleOwner, Observer {
            isSuccess: Boolean ->

            isSuccess.let {
                progressbar.visibility = if (it) View.GONE else View.VISIBLE
            }


        })

        placemarksViewModel.getListPlacemarksErrorMessage().observe(viewLifecycleOwner, Observer {
            isError: Boolean ->
            isError.let {
                progressbar.visibility = if (it) View.GONE else View.VISIBLE

            }
        })

        placemarksViewModel.getListPlacemarksErrorCode().observe(viewLifecycleOwner, Observer {
            isErrorCode: Boolean ->
            isErrorCode.let {
                progressbar.visibility = if (it) View.GONE else View.VISIBLE
            }
        })

    }


}
