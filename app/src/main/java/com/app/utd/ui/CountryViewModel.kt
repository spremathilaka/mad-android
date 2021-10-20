package com.app.utd.ui

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CountryViewModel(private val countryRepository: CountryRepository) : ViewModel() {

    private val mutableViewState =
        MutableLiveData(CountryContract.ViewState())
    val viewState: LiveData<CountryContract.ViewState>
        get() = mutableViewState

    val query = MutableStateFlow("")

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

    fun filterCountry(searchTerm: String) {
        query.value = searchTerm


        viewModelScope.launch {
            query.debounce(300)
                .filter { query ->
                    return@filter query.isNotEmpty()
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    countryRepository.searchCountry(query).catch {
                        emitAll(flowOf(emptyList()))
                    }
                }
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    mutableViewState.update(isLoading = false, data = result)
                }
        }
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
