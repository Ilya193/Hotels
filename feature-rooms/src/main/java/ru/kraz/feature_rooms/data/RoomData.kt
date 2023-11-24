package ru.kraz.feature_rooms.data

import ru.kraz.common.core.ToMapper
import ru.kraz.feature_rooms.domain.RoomDomain

data class RoomData(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer: String,
    val peculiarities: List<String>,
    val imageUrls: List<String>,
) : ToMapper<RoomDomain> {
    override fun map(): RoomDomain =
        RoomDomain(
            id,
            name,
            price,
            pricePer,
            peculiarities,
            imageUrls
        )

}