package ru.kraz.hotels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kraz.feature_hotel.presentation.HotelRouter
import ru.kraz.feature_paid.presentation.PaidRouter
import ru.kraz.feature_reservation.presentation.ReservationRouter
import ru.kraz.feature_rooms.presentation.RoomsRouter

interface Navigation<T> {
    fun read(): LiveData<T>
    fun update(value: T)

    class Base : Navigation<Screen>, HotelRouter, RoomsRouter, ReservationRouter, PaidRouter {
        private val liveData = MutableLiveData<Screen>()

        override fun read(): LiveData<Screen> = liveData

        override fun update(value: Screen) {
            liveData.value = value
        }

        override fun openRoom(title: String) {
            update(RoomsScreen(title))
        }

        override fun openReservation() {
            update(ReservationScreen)
        }

        override fun openPaid() {
            update(PaidScreen)
        }

        override fun openHotel() {
            update(HotelScreenWithClear)
        }

        override fun coup() {
            update(Screen.Coup)
        }

        override fun comeback() {
            update(Screen.Pop)
        }
    }
}