package ru.kraz.feature_reservation.presentation

import ru.kraz.common.presentation.ToUiMapper
import ru.kraz.feature_reservation.domain.InfoHotelDomain

class BaseToInfoHotelUiMapper : ToUiMapper<InfoHotelDomain, HotelUi.InfoHotel> {
    override fun map(data: InfoHotelDomain): HotelUi.InfoHotel =
        HotelUi.InfoHotel(
            data.id,
            data.hotelName,
            data.hotelAddress,
            data.horating,
            data.ratingName,
            data.departure,
            data.arrivalCountry,
            data.tourDataStart + " - " + data.tourDataStop,
            data.numberOfNights,
            data.room,
            data.nutrition,
            data.tourPrice,
            data.fuelCharge,
            data.serviceCharge,
            data.tourPrice + data.fuelCharge + data.serviceCharge
        )
}