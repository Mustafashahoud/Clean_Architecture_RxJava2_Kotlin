package com.android.cleanarch.bitcoin.data.common.errorhandling

import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorEntity
import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GeneralErrorHandlerImpl @Inject constructor() : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.Network
            is HttpException -> ErrorEntity.Server
            else -> ErrorEntity.Unknown
            //Other errors can be added
        }
    }
}