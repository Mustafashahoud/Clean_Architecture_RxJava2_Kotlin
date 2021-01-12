package com.android.cleanarch.bitcoinapp.util

import java.text.NumberFormat
import java.util.*

object CurrencyUtils {

    /**
     * @param amount the amount
     * @param locale  the desired locale, ny default it is Locale.US
     */
    @JvmStatic
    fun formatAmount(amount: Float, locale: Locale = Locale.US): String {
        return NumberFormat.getCurrencyInstance(locale).format(amount)
    }
}