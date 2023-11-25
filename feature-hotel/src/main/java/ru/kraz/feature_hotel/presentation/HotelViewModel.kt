package ru.kraz.feature_hotel.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kraz.common.core.ResourceProvider
import ru.kraz.common.core.ResultFDS
import ru.kraz.common.presentation.BaseViewModel
import ru.kraz.common.presentation.ToUiMapper
import ru.kraz.feature_hotel.domain.FetchHotelUseCase
import ru.kraz.feature_hotel.domain.HotelDomain

class HotelViewModel(
    private val hotelRouter: HotelRouter,
    private val fetchHotelUseCase: FetchHotelUseCase,
    private val mapper: ToUiMapper<HotelDomain, HotelUi.Success>,
    private val resourceProvider: ResourceProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<HotelUiState>(hotelRouter) {

    fun fetchHotel() = viewModelScope.launch(dispatcher) {
        _uiState.postValue(HotelUiState.Loading)
        when (val hotel = fetchHotelUseCase()) {
            is ResultFDS.Success -> _uiState.postValue(HotelUiState.Success(mapper.map(hotel.data)))
            is ResultFDS.Error -> _uiState.postValue(HotelUiState.Error(resourceProvider.getString(hotel.e)))
        }
    }

    fun openRoom(title: String) = hotelRouter.openRoom(title)
}