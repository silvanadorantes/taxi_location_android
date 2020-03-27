package com.example.silvanadorantescode.taxi_location_android.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.silvanadorantescode.taxi_location_android.BR
import com.example.silvanadorantescode.taxi_location_android.app.network.data.placemarks.PlacemarksListItem
import com.example.silvanadorantescode.taxi_location_android.presentation.viewmodel.PlacemarksViewModel


/**
 * Created by SilvanaDorantes on 21/03/20.
 */
class PlacemarksAdapter(var placemarksViewModel: PlacemarksViewModel, var resource: Int) : RecyclerView.Adapter<PlacemarksAdapter.PlacemarksViewHolder>() {

     var listPlacemarks: List<PlacemarksListItem>? = null

    fun setPlacemarksList(listPlacemarks: List<PlacemarksListItem>?){
        this.listPlacemarks = listPlacemarks
    }

    override fun getItemCount(): Int {
        return listPlacemarks?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacemarksViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return PlacemarksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacemarksViewHolder, position: Int) {
        holder.setData(placemarksViewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun getLayoutIdForPosition(position: Int): Int{
        return resource
    }

    class PlacemarksViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setData(placemarksViewModel: PlacemarksViewModel, position:Int){
            binding?.setVariable(BR.model, placemarksViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }
    }
}