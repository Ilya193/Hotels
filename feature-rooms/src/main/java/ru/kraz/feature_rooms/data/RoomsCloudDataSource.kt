package ru.kraz.feature_rooms.data

interface RoomsCloudDataSource {
    suspend fun fetchRooms(): Rooms
}