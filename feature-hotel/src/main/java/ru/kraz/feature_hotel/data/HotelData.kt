package ru.kraz.feature_hotel.data

import ru.kraz.common.core.ToMapper
import ru.kraz.feature_hotel.domain.HotelDomain

data class HotelData(
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
) : ToMapper<HotelDomain> {
    override fun map(): HotelDomain = HotelDomain(
        id,
        name,
        address,
        minimalPrice,
        priceForIt,
        rating,
        ratingName,
        imageUrls,
        description,
        peculiarities
    )
}