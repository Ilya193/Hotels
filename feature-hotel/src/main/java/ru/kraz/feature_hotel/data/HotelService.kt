package ru.kraz.feature_hotel.data

import retrofit2.http.GET

interface HotelService {
    @GET("d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun fetchHotel(): HotelCloud
}