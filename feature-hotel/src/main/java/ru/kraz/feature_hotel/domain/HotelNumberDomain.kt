package ru.kraz.feature_hotel.domain

data class HotelNumberDomain(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer: String,
    val peculiarities: List<String>,
    val imageUrls: List<String>,
)