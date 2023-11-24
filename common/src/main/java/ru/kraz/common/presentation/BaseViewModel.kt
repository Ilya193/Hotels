package ru.kraz.common.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T>(
    private val router: Router
) : ViewModel() {

    protected val _uiState = MutableLiveData<T>()
    val uiState: LiveData<T> get() = _uiState

    fun coup() = router.coup()
}