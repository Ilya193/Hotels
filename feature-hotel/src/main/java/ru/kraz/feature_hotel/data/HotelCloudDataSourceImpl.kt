package ru.kraz.feature_hotel.data

class HotelCloudDataSourceImpl(private val service: HotelService): HotelCloudDataSource {
    override suspend fun fetchHotel(): HotelCloud = service.fetchHotel()
}