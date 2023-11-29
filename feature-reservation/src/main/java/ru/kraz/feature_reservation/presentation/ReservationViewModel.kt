package ru.kraz.feature_reservation.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kraz.common.core.ResourceProvider
import ru.kraz.common.core.ResultFDS
import ru.kraz.common.presentation.BaseViewModel
import ru.kraz.feature_reservation.domain.FetchInfoHotelUseCase

class ReservationViewModel(
    private val reservationRouter: ReservationRouter,
    private val fetchInfoHotelUseCase: FetchInfoHotelUseCase,
    private val infoHotelMapper: BaseToInfoHotelUiMapper,
    private val resourceProvider: ResourceProvider,
) : BaseViewModel<HotelUiState>(reservationRouter) {

    private var count = 1
    private val _tourists = MutableLiveData<EventWrapper<TouristsState>>(EventWrapper.Single(TouristsState.Initial(count)))
    val tourists: LiveData<EventWrapper<TouristsState>> get() = _tourists

    fun fetchInfoHotel() = viewModelScope.launch {
        _uiState.value = HotelUiState.Loading
        when (val hotel = fetchInfoHotelUseCase()) {
            is ResultFDS.Success -> {
                val infoHotel = infoHotelMapper.map(hotel.data)
                _uiState.value = HotelUiState.Success(infoHotel)
            }
            is ResultFDS.Error -> _uiState.value = HotelUiState.Error(resourceProvider.getString(hotel.e))
        }
    }


    fun add() {
        if (count < 9) _tourists.value = EventWrapper.Single(TouristsState.Add(++count))
    }

    fun openPaid() = reservationRouter.openPaid()

    override fun coup() {
        super.coup()
        _tourists.value = EventWrapper.Single(TouristsState.Initial(count))
    }
}

sealed interface TouristsState {

    data class Initial(val count: Int) : TouristsState
    data class Add(val count: Int) : TouristsState
}