package ru.kraz.feature_rooms.presentation

import ru.kraz.common.presentation.ToUiMapper
import ru.kraz.feature_rooms.domain.RoomDomain

class BaseToRoomUiMapper : ToUiMapper<List<RoomDomain>, List<RoomUi.Success>> {
    override fun map(data: List<RoomDomain>): List<RoomUi.Success> =
        data.map {
            RoomUi.Success(
                it.id,
                it.name,
                it.price,
                it.pricePer,
                it.peculiarities,
                it.imageUrls
            )
        }
}