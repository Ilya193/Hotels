package ru.kraz.feature_reservation.presentation

import ru.kraz.common.presentation.ToUiMapper
import ru.kraz.feature_reservation.domain.InfoHotelDomain

class BaseToInfoCommonUiMapper : ToUiMapper<InfoHotelDomain, HotelUi.InfoCommon> {
    override fun map(data: InfoHotelDomain): HotelUi.InfoCommon =
        HotelUi.InfoCommon(
            data.tourPrice,
            data.fuelCharge,
            data.serviceCharge,
            data.tourPrice + data.fuelCharge + data.serviceCharge
        )
}