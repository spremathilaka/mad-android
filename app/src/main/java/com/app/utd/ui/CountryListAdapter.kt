package com.app.utd.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.app.utd.databinding.ItemCountryBinding

class CountryListAdapter : ListAdapter<Country, CountryViewHolder>(CountryDiffUtilCallback()) {

    private val countryList: ArrayList<Country> = ArrayList()
    private var filterCountryList: ArrayList<Country> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val countryBinding = ItemCountryBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )

        return CountryViewHolder(
            countryBinding
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(filterCountryList[position])
    }


    fun updateCountryList(newList: List<Country>) {

        countryList.clear()
        countryList.addAll(newList)

        filterCountryList.clear()
        filterCountryList.addAll(newList)

        submitList(newList)
    }
}
