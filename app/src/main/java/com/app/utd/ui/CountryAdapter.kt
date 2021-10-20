package com.app.utd.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.utd.databinding.ItemCountryBinding

class CountryAdapter :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>(), Filterable {

    private val countryList: ArrayList<Country> = ArrayList()
    private var filterCountryList: ArrayList<Country> = ArrayList()


    class ViewHolder(private val countryBinding: ItemCountryBinding) :
        RecyclerView.ViewHolder(countryBinding.root) {

        fun bind(country: Country) {
            countryBinding.uiData = country
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val countryBinding = ItemCountryBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )

        return ViewHolder(
            countryBinding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterCountryList[position])
    }

    fun updateCountryList(newList: List<Country>) {
        countryList.clear()
        countryList.addAll(newList)

        filterCountryList.clear()
        filterCountryList.addAll(newList)

        //TODO use Diff util call bakc here for performance issues
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = filterCountryList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterCountryList = countryList
                } else {
                    val countryFilterResult = countryList.filter {
                        it.countryName.lowercase().contains(charSearch)
                    }

                    filterCountryList = countryFilterResult as ArrayList<Country>
                }
                val filterResult = FilterResults()
                filterResult.values = filterCountryList
                filterResult.count = filterCountryList.size
                return filterResult

            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterCountryList = results?.values as ArrayList<Country>
                notifyDataSetChanged()
            }
        }
    }
}
