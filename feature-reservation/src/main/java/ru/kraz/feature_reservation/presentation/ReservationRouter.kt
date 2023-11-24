package ru.kraz.feature_reservation.presentation

import ru.kraz.common.presentation.Router

interface ReservationRouter: Router {
    fun openPaid()
}