package ru.kraz.common.presentation

import androidx.lifecycle.ViewModel

abstract class BaseViewModel(
    private val router: Router
) : ViewModel() {

    fun coup() = router.coup()
}