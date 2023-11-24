package ru.kraz.feature_hotel.presentation

sealed class HotelUi {

    data class Success(
        val id: Int,
        val name: String,
        val address: String,
        val minimalPrice: Int,
        val priceForIt: String,
        val rating: Int,
        val ratingName: String,
        val imageUrls: List<String>,
        val description: String,
        val peculiarities: List<String>,
    ) : HotelUi()
}

sealed interface HotelUiState {

    data class Success(val hotel: HotelUi.Success): HotelUiState
    data class Error(val msg: String): HotelUiState
    data object Loading : HotelUiState
}