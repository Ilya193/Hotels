package ru.kraz.feature_reservation.data

import retrofit2.http.GET

interface ReservationService {
    @GET("63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun fetchInfoHotel(): InfoHotelCloud
}