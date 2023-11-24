package ru.kraz.feature_reservation.domain

import ru.kraz.common.core.ResultFDS

class FetchInfoHotelUseCase(private val repository: ReservationRepository) {
    suspend operator fun invoke(): ResultFDS<InfoHotelDomain> = repository.fetchInfoHotel()
}