package ru.kraz.feature_hotel.domain

import ru.kraz.common.core.ResultFDS

interface HotelRepository {
    suspend fun fetchHotel(): ResultFDS<HotelDomain>
}