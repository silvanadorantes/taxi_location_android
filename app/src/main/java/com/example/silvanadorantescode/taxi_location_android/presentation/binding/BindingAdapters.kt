package com.example.silvanadorantescode.taxi_location_android.presentation.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by SilvanaDorantes on 20/03/20.
 */

@BindingAdapter("visible")
fun View.bindVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}