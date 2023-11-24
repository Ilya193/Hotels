package ru.kraz.feature_rooms.domain

import ru.kraz.common.core.ResultFDS

class FetchRoomsUseCase(private val repository: RoomsRepository) {
    suspend operator fun invoke(): ResultFDS<List<RoomDomain>> =
        repository.fetchRooms()
}