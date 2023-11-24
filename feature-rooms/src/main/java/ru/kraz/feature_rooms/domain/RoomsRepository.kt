package ru.kraz.feature_rooms.domain

import ru.kraz.common.core.ResultFDS

interface RoomsRepository {
    suspend fun fetchRooms(): ResultFDS<List<RoomDomain>>
}