package com.android.cleanarch.bitcoin.domain.base.errorhandling

sealed class ErrorEntity {

    object Network : ErrorEntity()

    object Server : ErrorEntity()

    object Unknown : ErrorEntity()
}