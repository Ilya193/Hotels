package ru.kraz.feature_rooms.data

import retrofit2.http.GET

interface RoomsService {
    @GET("8b532701-709e-4194-a41c-1a903af00195")
    suspend fun fetchRooms(): Rooms
}

