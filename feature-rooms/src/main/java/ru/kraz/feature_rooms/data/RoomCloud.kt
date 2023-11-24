package ru.kraz.feature_rooms.data

import com.google.gson.annotations.SerializedName
import ru.kraz.common.core.ToMapper

data class Rooms(
    @SerializedName("rooms")
    val list: List<RoomCloud>,
)

data class RoomCloud(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_per")
    val pricePer: String,
    @SerializedName("peculiarities")
    val peculiarities: List<String>,
    @SerializedName("image_urls")
    val imageUrls: List<String>,
) : ToMapper<RoomData> {
    override fun map(): RoomData =
        RoomData(
            id,
            name,
            price,
            pricePer,
            peculiarities,
            imageUrls
        )

}