package ru.kraz.common.presentation

interface ToUiMapper<T, R> {
    fun map(data: T): R
}