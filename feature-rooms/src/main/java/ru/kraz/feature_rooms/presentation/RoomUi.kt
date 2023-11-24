package ru.kraz.feature_rooms.presentation

import ru.kraz.common.presentation.Comparing

sealed class RoomUi : Comparing<RoomUi> {

    override fun same(item: RoomUi): Boolean = false
    override fun sameContent(item: RoomUi): Boolean = false

    data class Success(
        val id: Int,
        val name: String,
        val price: Int,
        val pricePer: String,
        val peculiarities: List<String>,
        val imageUrls: List<String>,
    ) : RoomUi() {
        override fun same(item: RoomUi): Boolean =
            item is Success && id == item.id

        override fun sameContent(item: RoomUi): Boolean =
            this == item
    }

    data class Error(
        val message: String = "",
    ) : RoomUi()
}

sealed interface RoomUiState {
    data class Success(val rooms: List<RoomUi.Success>): RoomUiState
    data class Error(val msg: String): RoomUiState
    data object Loading : RoomUiState
}