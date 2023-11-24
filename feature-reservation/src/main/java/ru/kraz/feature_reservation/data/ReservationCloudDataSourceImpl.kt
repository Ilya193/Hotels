package ru.kraz.feature_reservation.data

class ReservationCloudDataSourceImpl(
    private val service: ReservationService
): ReservationCloudDataSource {
    override suspend fun fetchInfoHotel(): InfoHotelCloud = service.fetchInfoHotel()
}