package ru.kraz.feature_hotel.data

import ru.kraz.common.core.Repository
import ru.kraz.common.core.ResultFDS
import ru.kraz.feature_hotel.domain.HotelDomain
import ru.kraz.feature_hotel.domain.HotelRepository

class HotelRepositoryImpl(
    private val cloudDataSource: HotelCloudDataSource,
) : HotelRepository, Repository() {
    override suspend fun fetchHotel(): ResultFDS<HotelDomain> {
        return handleExceptions {
            val hotel = cloudDataSource.fetchHotel().map()
            ResultFDS.Success(hotel.map())
        }
    }
}