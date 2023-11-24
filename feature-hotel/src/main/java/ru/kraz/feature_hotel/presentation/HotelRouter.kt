package ru.kraz.feature_hotel.presentation

import ru.kraz.common.presentation.Router

interface HotelRouter: Router {
    fun openRoom(title: String)
}