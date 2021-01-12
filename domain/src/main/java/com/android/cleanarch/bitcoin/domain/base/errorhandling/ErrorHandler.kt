package com.android.cleanarch.bitcoin.domain.base.errorhandling

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}