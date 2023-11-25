package ru.kraz.feature_reservation.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kraz.common.core.ResourceProvider
import ru.kraz.common.core.ResultFDS
import ru.kraz.common.presentation.BaseViewModel
import ru.kraz.feature_reservation.domain.FetchInfoHotelUseCase

class ReservationViewModel(
    private val reservationRouter: ReservationRouter,
    private val fetchInfoHotelUseCase: FetchInfoHotelUseCase,
    private val infoHotelMapper: BaseToInfoHotelUiMapper,
    private val infoCommonMapper: BaseToInfoCommonUiMapper,
    private val resourceProvider: ResourceProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<HotelUiState>(reservationRouter) {

    private var count = 0
    private val list = mutableListOf<HotelUi>()

    fun fetchInfoHotel() = viewModelScope.launch(dispatcher) {
        if (list.isEmpty()) {
            _uiState.postValue(HotelUiState.Loading)
            when (val hotel = fetchInfoHotelUseCase()) {
                is ResultFDS.Success -> {
                    val infoHotel = infoHotelMapper.map(hotel.data)
                    val infoCommon = infoCommonMapper.map(hotel.data)
                    list.addAll(
                        listOf(
                            infoHotel,
                            HotelUi.InfoBuyer,
                            HotelUi.Tourist(0, false),
                            HotelUi.Tourist(1),
                            HotelUi.AddTourist,
                            infoCommon
                        )
                    )
                    count = 2
                    _uiState.postValue(HotelUiState.Success(list.toMutableList()))
                }
                is ResultFDS.Error -> _uiState.postValue(HotelUiState.Error(resourceProvider.getString(hotel.e)))
            }
        }
    }

    fun setHidden(tourist: HotelUi.Tourist) {
        val index = list.indexOf(tourist)
        list[index] = HotelUi.Tourist(tourist.which, !tourist.isHidden)
        _uiState.value = HotelUiState.Success(list.toMutableList())
    }

    fun add(tourist: HotelUi.AddTourist) {
        if (count < 10) {
            list.add(list.indexOf(tourist), HotelUi.Tourist(count++))
            _uiState.value = HotelUiState.Success(list.toMutableList())
        }
    }

    fun openPaid() = reservationRouter.openPaid()
}