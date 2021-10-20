package com.app.utd.ui

import androidx.recyclerview.widget.RecyclerView
import com.app.utd.databinding.ItemCountryBinding

class CountryViewHolder(private val countryBinding: ItemCountryBinding) :
    RecyclerView.ViewHolder(countryBinding.root) {

    fun bind(country: Country) {
        countryBinding.uiData = country
    }
}
