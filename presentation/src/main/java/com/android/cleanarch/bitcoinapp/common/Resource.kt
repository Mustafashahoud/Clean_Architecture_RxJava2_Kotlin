package com.android.cleanarch.bitcoinapp.common

import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorEntity

// A generic class that contains data and status about loading this data.
sealed class Resource<out T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val error: ErrorEntity? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading : Resource<Nothing>()
    class Error(error: ErrorEntity) : Resource<Nothing>(error = error)
}
