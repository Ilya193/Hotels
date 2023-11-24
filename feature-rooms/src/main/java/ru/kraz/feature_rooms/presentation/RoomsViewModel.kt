package ru.kraz.feature_rooms.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kraz.common.presentation.BaseViewModel
import ru.kraz.common.core.ResourceProvider
import ru.kraz.common.core.ResultFDS
import ru.kraz.feature_rooms.domain.FetchRoomsUseCase

class RoomsViewModel(
    private val router: RoomsRouter,
    private val fetchRoomsUseCase: FetchRoomsUseCase,
    private val mapper: BaseToRoomUiMapper,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(router) {

    private val _uiState = MutableLiveData<RoomUiState>()
    val uiState: LiveData<RoomUiState> get() = _uiState

    fun fetchRooms() = viewModelScope.launch {
        _uiState.value = RoomUiState.Loading
        when (val rooms = fetchRoomsUseCase()) {
            is ResultFDS.Success -> _uiState.value = RoomUiState.Success(mapper.map(rooms.data))
            is ResultFDS.Error -> _uiState.value = RoomUiState.Error(resourceProvider.getString(rooms.e))
        }
    }

    fun openReservation() = router.openReservation()
}