package ru.kraz.common.core

import android.content.Context
import ru.kraz.common.R

interface ResourceProvider {
    fun getString(errorType: ErrorType): String

    class Base(
        private val context: Context,
    ) : ResourceProvider {
        override fun getString(errorType: ErrorType): String {
            val id = when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.no_connection
                ErrorType.SERVICE_UNAVAILABLE -> R.string.service_unavailable
                ErrorType.GENERIC_ERROR -> R.string.something_went_wrong
            }
            return context.getString(id)
        }
    }
}