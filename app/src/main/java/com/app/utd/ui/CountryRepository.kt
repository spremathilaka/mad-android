package com.app.utd.ui

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CountryRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getCountryList(): List<Country> {
        return withContext(dispatcher) {
            delay(4000)
            getMockCountryList()
        }
    }

    fun searchCountry(query: String): Flow<List<Country>> {
        return flow {
            delay(2000)
            emit(getMockCountryList().filter { it.countryName == query })
        }
    }

    private fun getMockCountryList(): List<Country> = listOf(
        Country("Sri Lanka", "LK"),
        Country("India", "IND"),
        Country("Us", "US"),
        Country("Canada", "CAN")
    )

}
