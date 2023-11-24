package ru.kraz.feature_reservation.data

interface ReservationCloudDataSource {
    suspend fun fetchInfoHotel(): InfoHotelCloud
}