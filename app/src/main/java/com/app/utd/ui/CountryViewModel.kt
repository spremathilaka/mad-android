package com.app.utd.ui

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CountryViewModel(private val countryRepository: CountryRepository) : ViewModel() {

    private val mutableViewState =
        MutableLiveData(CountryContract.ViewState())
    val viewState: LiveData<CountryContract.ViewState>
        get() = mutableViewState

    fun getCountryList() {
        viewModelScope.launch {
            mutableViewState.update(isLoading = true)
            val data = countryRepository.getCountryList()
            mutableViewState.update(isLoading = false, data = data)
        }

    }


    private fun MutableLiveData<CountryContract.ViewState>.update(
        isLoading: Boolean = false,
        error: String? = null,
        data: List<Country> = emptyList()
    ) {
        value = value?.copy(isLoading = isLoading, error = error, data = data)

    }

}

class CountryViewModelFactory(private val countryRepository: CountryRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            return CountryViewModel(countryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
