package ru.kraz.feature_rooms.presentation

import ru.kraz.common.presentation.Router

interface RoomsRouter: Router {
    fun openReservation()
}