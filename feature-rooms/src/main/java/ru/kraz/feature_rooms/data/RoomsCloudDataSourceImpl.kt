package ru.kraz.feature_rooms.data

class RoomsCloudDataSourceImpl(
    private val service: RoomsService
) : RoomsCloudDataSource {
    override suspend fun fetchRooms(): Rooms = service.fetchRooms()
}