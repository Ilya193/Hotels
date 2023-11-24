package ru.kraz.feature_reservation.data

import ru.kraz.common.core.Repository
import ru.kraz.common.core.ResultFDS
import ru.kraz.feature_reservation.domain.InfoHotelDomain
import ru.kraz.feature_reservation.domain.ReservationRepository

class ReservationRepositoryImpl(
    private val cloudDataSource: ReservationCloudDataSource
) : ReservationRepository, Repository() {
    override suspend fun fetchInfoHotel(): ResultFDS<InfoHotelDomain> {
        return handleExceptions {
            val hotel = cloudDataSource.fetchInfoHotel().map()
            ResultFDS.Success(hotel.map())
        }
    }
}