package ru.kraz.feature_reservation.domain

import ru.kraz.common.core.ResultFDS

interface ReservationRepository {
    suspend fun fetchInfoHotel(): ResultFDS<InfoHotelDomain>
}