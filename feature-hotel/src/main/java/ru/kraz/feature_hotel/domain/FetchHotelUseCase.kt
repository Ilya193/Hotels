package ru.kraz.feature_hotel.domain

import ru.kraz.common.core.ResultFDS

class FetchHotelUseCase(private val repository: HotelRepository) {
    suspend operator fun invoke(): ResultFDS<HotelDomain> = repository.fetchHotel()
}