package ru.kraz.feature_hotel.presentation

import ru.kraz.common.presentation.ToUiMapper
import ru.kraz.feature_hotel.domain.HotelDomain

class BaseToHotelUiMapper : ToUiMapper<HotelDomain, HotelUi.Success> {
    override fun map(data: HotelDomain): HotelUi.Success =
        HotelUi.Success(
            data.id,
            data.name,
            data.address,
            data.minimalPrice,
            data.priceForIt,
            data.rating,
            data.ratingName,
            data.imageUrls,
            data.description,
            data.peculiarities
        )
}