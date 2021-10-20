package com.app.utd.ui

import androidx.recyclerview.widget.DiffUtil

class CountryDiffUtilCallback :
    DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
        oldItem == newItem
}
