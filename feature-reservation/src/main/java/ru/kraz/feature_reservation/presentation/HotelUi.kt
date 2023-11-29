package ru.kraz.feature_reservation.presentation

sealed interface HotelUi {
    data class InfoHotel(
        val id: Int,
        val hotelName: String,
        val hotelAddress: String,
        val horating: Int,
        val ratingName: String,
        val departure: String,
        val arrivalCountry: String,
        val tourDataStartToStop: String,
        val numberOfNights: Int,
        val room: String,
        val nutrition: String,
        val tourPrice: Int,
        val fuelCharge: Int,
        val serviceCharge: Int,
        val toPay: Int,
    ) : HotelUi
}

sealed interface HotelUiState {

    data class Success(val data: HotelUi.InfoHotel) : HotelUiState
    data class Error(val msg: String) : HotelUiState
    data object Loading : HotelUiState
}