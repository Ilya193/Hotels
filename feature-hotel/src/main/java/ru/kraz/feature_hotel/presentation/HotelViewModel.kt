package ru.kraz.feature_hotel.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kraz.common.presentation.BaseViewModel
import ru.kraz.common.presentation.ToUiMapper
import ru.kraz.common.core.ResourceProvider
import ru.kraz.feature_hotel.domain.FetchHotelUseCase
import ru.kraz.common.core.ResultFDS
import ru.kraz.feature_hotel.domain.HotelDomain

class HotelViewModel(
    private val hotelRouter: HotelRouter,
    private val fetchHotelUseCase: FetchHotelUseCase,
    private val mapper: ToUiMapper<HotelDomain, HotelUi.Success>,
    private val resourceProvider: ResourceProvider
): BaseViewModel(hotelRouter) {

    private val _uiState = MutableLiveData<HotelUiState>()
    val uiState: LiveData<HotelUiState> get() = _uiState

    fun fetchHotel() = viewModelScope.launch {
        _uiState.value = HotelUiState.Loading
        when (val hotel = fetchHotelUseCase()) {
            is ResultFDS.Success -> _uiState.value = HotelUiState.Success(mapper.map(hotel.data))
            is ResultFDS.Error -> _uiState.value = HotelUiState.Error(resourceProvider.getString(hotel.e))
        }
    }

    fun openRoom(title: String) = hotelRouter.openRoom(title)
}