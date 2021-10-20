package com.app.utd.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.app.utd.rule.CoroutinesTestRule
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class CountryViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var testObserver: Observer<CountryContract.ViewState>

    @Mock
    private lateinit var repository: CountryRepository

    @Captor
    private lateinit var viewStateCaptor: ArgumentCaptor<CountryContract.ViewState>

    private lateinit var viewModel: CountryViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CountryViewModel(repository)
    }


    @Test
    fun `give loadData success, when call get country, should return  country list`() {
        runBlockingTest {

            //given
            `when`(repository.getCountryList()).thenReturn(getListOfCountries())

            //when
            viewModel.viewState.observeForever(testObserver)
            viewModel.getCountryList()

            //then
            with(viewStateCaptor) {
                verify(testObserver, times(3)).onChanged(capture())

                Assert.assertEquals(false, allValues[0].isLoading)
                Assert.assertEquals(true, allValues[1].isLoading)
                Assert.assertEquals(false, allValues[2].isLoading)
            }
        }
    }

    private fun getListOfCountries() = listOf(
        Country("Sri Lanka", "LK"),
        Country("India", "IND"),
        Country("Us", "US"),
        Country("Canada", "CAN")
    )
}
