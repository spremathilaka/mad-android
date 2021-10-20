package com.app.utd.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.utd.R
import com.app.utd.databinding.ActivityCountrySearchBinding

class CountrySearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var activityBinding: ActivityCountrySearchBinding

    private lateinit var viewModel: CountryViewModel

    private val dataAdapter: CountryAdapter = CountryAdapter()

    private lateinit var viewModelFactory: CountryViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject()
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        dataAdapter.filter.filter(newText)
        return false
    }

    private fun inject() {
        val countryRepository = CountryRepository()
        viewModelFactory = CountryViewModelFactory(countryRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CountryViewModel::class.java)

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
