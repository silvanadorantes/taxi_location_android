package com.example.silvanadorantescode.taxi_location_android.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.databinding.ItemListPlacemarksBinding
import com.example.silvanadorantescode.taxi_location_android.presentation.ui.ListPlacemarksFragmentDirections


/**
 * Created by SilvanaDorantes on 21/03/20.
 */
class PlacemarksAdapter : ListAdapter<PlacemarksListItem, PlacemarksAdapter.PlacemarksViewHolder>(PlacemarksDiffCallback()) {

    companion object{
        private val TAG = PlacemarksAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacemarksViewHolder {

        return PlacemarksViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_list_placemarks, parent,false))
    }

    override fun onBindViewHolder(holder: PlacemarksViewHolder, position: Int) {
       val placemark = getItem(position)
        holder.bind(placemark)
    }



    class PlacemarksViewHolder(private val binding: ItemListPlacemarksBinding): RecyclerView.ViewHolder(binding.root){


        init {
            binding.setClickListener {
                binding.setClickListener {
                    binding.placemarksListItem?.let {placemarksListItem ->
                        navigateToPlacemarksMap(placemarksListItem, it)

                    }
                }
            }
        }

        private fun navigateToPlacemarksMap(placemarksListItem:PlacemarksListItem, view: View){
            val direction = ListPlacemarksFragmentDirections.actionToListplacemarksMapdetailFramgent(placemarksListItem)
            view.findNavController().navigate(direction)
        }

        fun bind(item: PlacemarksListItem){
              binding.apply {
                placemarksListItem = item
                  Log.d(TAG, "placemarks" + " " + placemarksListItem)
                  Log.d(TAG, "item" + " " + item)
                  executePendingBindings()
              }
        }

    }

    private class PlacemarksDiffCallback: DiffUtil.ItemCallback<PlacemarksListItem>(){
        override fun areItemsTheSame(oldItem: PlacemarksListItem, newItem: PlacemarksListItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PlacemarksListItem, newItem: PlacemarksListItem): Boolean {
            return oldItem == newItem
        }
    }


}