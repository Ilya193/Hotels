package ru.kraz.feature_reservation.data

import ru.kraz.common.core.ToMapper
import ru.kraz.feature_reservation.domain.InfoHotelDomain

data class InfoHotelData(
    val id: Int,
    val hotelName: String,
    val hotelAddress: String,
    val horating: Int,
    val ratingName: String,
    val departure: String,
    val arrivalCountry: String,
    val tourDataStart: String,
    val tourDataStop: String,
    val numberOfNights: Int,
    val room: String,
    val nutrition: String,
    val tourPrice: Int,
    val fuelCharge: Int,
    val serviceCharge: Int,
) : ToMapper<InfoHotelDomain> {
    override fun map(): InfoHotelDomain =
        InfoHotelDomain(
            id,
            hotelName,
            hotelAddress,
            horating,
            ratingName,
            departure,
            arrivalCountry,
            tourDataStart,
            tourDataStop,
            numberOfNights,
            room,
            nutrition,
            tourPrice,
            fuelCharge,
            serviceCharge
        )
}