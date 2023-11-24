package ru.kraz.feature_rooms.data

import ru.kraz.common.core.Repository
import ru.kraz.common.core.ResultFDS
import ru.kraz.feature_rooms.domain.RoomDomain
import ru.kraz.feature_rooms.domain.RoomsRepository

class RoomsRepositoryImpl(
    private val cloudDataSource: RoomsCloudDataSource
) : RoomsRepository, Repository() {
    override suspend fun fetchRooms(): ResultFDS<List<RoomDomain>> {
        return handleExceptions {
            val rooms = cloudDataSource.fetchRooms().list.map { it.map() }
            ResultFDS.Success(rooms.map { it.map() })
        }
    }
}