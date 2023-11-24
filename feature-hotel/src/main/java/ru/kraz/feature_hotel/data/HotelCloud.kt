package ru.kraz.feature_hotel.data

import com.google.gson.annotations.SerializedName
import ru.kraz.common.core.ToMapper

data class HotelCloud(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("adress")
    val address: String,
    @SerializedName("minimal_price")
    val minimalPrice: Int,
    @SerializedName("price_for_it")
    val priceForIt: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("rating_name")
    val ratingName: String,
    @SerializedName("image_urls")
    val imageUrls: List<String>,
    @SerializedName("about_the_hotel")
    val aboutHotel: AboutHotelCloud,
) : ToMapper<HotelData> {
    override fun map(): HotelData = HotelData(
        id,
        name,
        address,
        minimalPrice,
        priceForIt,
        rating,
        ratingName,
        imageUrls,
        aboutHotel.description,
        aboutHotel.peculiarities
    )
}

data class AboutHotelCloud(
    @SerializedName("description")
    val description: String,
    @SerializedName("peculiarities")
    val peculiarities: List<String>,
)