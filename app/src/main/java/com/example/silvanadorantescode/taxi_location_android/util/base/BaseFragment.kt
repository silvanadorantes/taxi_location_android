package com.example.silvanadorantescode.taxi_location_android.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.silvanadorantescode.taxi_location_android.R
import com.example.silvanadorantescode.taxi_location_android.util.TaxiLocationToolbarView

/**
 * Created by SilvanaDorantes on 26/03/20.
 */

abstract open class BaseFragment: Fragment() {
    lateinit var toolbar: TaxiLocationToolbarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById<View>(R.id.taxi_location_toolbar) as TaxiLocationToolbarView
    }



    fun showFragment(fragment: Fragment, tag: String, animEnter: Int, animExit: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(animEnter, animExit)
            .add(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(null)
            .commit()



    }

    fun closeFragment(fragment: Fragment, animEnter: Int, animExit: Int) {
        requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(animEnter, animExit).remove(fragment).commit()
    }
}