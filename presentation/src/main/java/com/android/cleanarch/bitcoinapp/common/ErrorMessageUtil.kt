package com.android.cleanarch.bitcoinapp.common

import android.content.Context
import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorEntity
import com.android.cleanarch.bitcoinapp.R

/**
 * Is used in data binding "activity/fragment", never in ViewModel.
 */
object ErrorMessageUtil {
    @JvmStatic
    fun getErrorMessage(context: Context, error: ErrorEntity?): String {
        return when (error) {
            is ErrorEntity.Network -> context.resources.getString(R.string.network_error_message)
            is ErrorEntity.Server -> context.resources.getString(R.string.server_error_message)
            is ErrorEntity.Unknown -> context.resources.getString(R.string.unknown_error_message)
            else -> context.resources.getString(R.string.unknown_error_message)
        }
    }
}
