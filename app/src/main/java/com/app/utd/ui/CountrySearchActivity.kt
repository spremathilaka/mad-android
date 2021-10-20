package com.app.utd.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.utd.R
import com.app.utd.databinding.ActivityCountrySearchBinding

class CountrySearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var activityBinding: ActivityCountrySearchBinding

    private val dataAdapter: CountryListAdapter = CountryListAdapter()

    private val viewModel: CountryViewModel by viewModels {
        CountryViewModel.Factory(CountryRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityBinding = DataBindingUtil.setContentView<ActivityCountrySearchBinding>(
            this,
            R.layout.activity_country_search
        ).apply {
            lifecycleOwner = this@CountrySearchActivity
            viewModel = this@CountrySearchActivity.viewModel
        }

        setUpUi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.searcch_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            maxWidth = Integer.MAX_VALUE
            setIconifiedByDefault(true)
            setOnQueryTextListener(this@CountrySearchActivity)// Do not iconify the widget; expand it by default
        }
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        //dataAdapter.filter.filter(newText)
        viewModel.filterCountry(newText)
        return false
    }


    private fun setUpUi() {

        activityBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CountrySearchActivity)
            adapter = dataAdapter
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }

        observeViewState()
        viewModel.getCountryList()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(
            this
        ) {

            it?.let {
                dataAdapter.updateCountryList(it.data)
            }

        }
    }
}
