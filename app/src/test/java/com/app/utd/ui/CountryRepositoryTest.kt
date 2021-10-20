package com.app.utd.ui


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CountryRepositoryTest {

    private lateinit var countryRepository: CountryRepository

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        countryRepository = CountryRepository(testDispatcher)
    }

    @Test
    fun `getAll countries success, return list of countries`() {
        testScope.runBlockingTest {
            val countryList = countryRepository.getCountryList()
            val expectedList = listOf(
                Country("Sri Lanka", "LK"),
                Country("India", "IND"),
                Country("Us", "US"),
                Country("Canada", "CAN")
            )
            Assert.assertEquals(countryList, expectedList)
        }
    }
}
