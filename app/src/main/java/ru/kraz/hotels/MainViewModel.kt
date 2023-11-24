package ru.kraz.hotels

import androidx.lifecycle.ViewModel

class MainViewModel(
    private val navigation: Navigation<Screen>
): ViewModel() {

    fun init(first: Boolean) {
        if (first) navigation.update(HotelScreen())
    }

    fun liveData() = navigation.read()
}