package com.app.utd.ui.bindings

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}
