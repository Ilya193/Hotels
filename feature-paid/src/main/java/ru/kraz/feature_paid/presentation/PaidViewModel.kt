package ru.kraz.feature_paid.presentation

import ru.kraz.common.presentation.BaseViewModel

class PaidViewModel(
    private val paidRouter: PaidRouter
): BaseViewModel(paidRouter) {

    fun openHotel() = paidRouter.openHotel()
}