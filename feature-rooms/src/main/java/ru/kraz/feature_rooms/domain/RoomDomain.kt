package ru.kraz.feature_rooms.domain

data class RoomDomain(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer: String,
    val peculiarities: List<String>,
    val imageUrls: List<String>,
)