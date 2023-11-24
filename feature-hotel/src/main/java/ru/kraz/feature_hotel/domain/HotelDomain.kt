package ru.kraz.feature_hotel.domain

data class HotelDomain(
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
)