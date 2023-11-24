package ru.kraz.feature_hotel.data

interface HotelCloudDataSource {
    suspend fun fetchHotel(): HotelCloud
}
