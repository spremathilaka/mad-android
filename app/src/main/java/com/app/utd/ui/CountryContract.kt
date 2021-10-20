package com.app.utd.ui

interface CountryContract {

    data class ViewState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val data: List<Country> = emptyList()
    )

}
