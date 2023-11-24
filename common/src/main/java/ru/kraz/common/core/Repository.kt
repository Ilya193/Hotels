package ru.kraz.common.core

import android.net.http.HttpException
import java.net.UnknownHostException

abstract class Repository {
    suspend fun <T> handleExceptions(block: suspend () -> ResultFDS<T>): ResultFDS<T> {
        return try {
            block()
        } catch (e: UnknownHostException) {
            ResultFDS.Error(ErrorType.NO_CONNECTION)
        } catch (e: HttpException) {
            ResultFDS.Error(ErrorType.SERVICE_UNAVAILABLE)
        } catch (e: Exception) {
            ResultFDS.Error(ErrorType.GENERIC_ERROR)
        }
    }
}