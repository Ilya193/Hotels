package ru.kraz.common.core

sealed class ResultFDS<out T> {

    data class Success<T>(
        val data: T
    ): ResultFDS<T>()

    data class Error(
        val e: ErrorType
    ): ResultFDS<Nothing>()

}