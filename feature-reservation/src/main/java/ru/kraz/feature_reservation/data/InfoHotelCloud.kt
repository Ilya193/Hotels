package ru.kraz.feature_reservation.data

import com.google.gson.annotations.SerializedName
import ru.kraz.common.core.ToMapper

data class InfoHotelCloud(
    @SerializedName("id")
    val id: Int,
    @SerializedName("hotel_name")
    val hotelName: String,
    @SerializedName("hotel_adress")
    val hotelAddress: String,
    @SerializedName("horating")
    val horating: Int,
    @SerializedName("rating_name")
    val ratingName: String,
    @SerializedName("departure")
    val departure: String,
    @SerializedName("arrival_country")
    val arrivalCountry: String,
    @SerializedName("tour_date_start")
    val tourDataStart: String,
    @SerializedName("tour_date_stop")
    val tourDataStop: String,
    @SerializedName("number_of_nights")
    val numberOfNights: Int,
    @SerializedName("room")
    val room: String,
    @SerializedName("nutrition")
    val nutrition: String,
    @SerializedName("tour_price")
    val tourPrice: Int,
    @SerializedName("fuel_charge")
    val fuelCharge: Int,
    @SerializedName("service_charge")
    val serviceCharge: Int,
) : ToMapper<InfoHotelData> {
    override fun map(): InfoHotelData =
        InfoHotelData(
            id,
            hotelName,
            hotelAddress,
            horating,
            ratingName,
            departure,
            arrivalCountry,
            tourDataStart,
            tourDataStop,
            numberOfNights,
            room,
            nutrition,
            tourPrice,
            fuelCharge,
            serviceCharge
        )
}