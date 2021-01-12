package com.android.cleanarch.bitcoinapp.features.bitcoinchart.model

import com.github.mikephil.charting.data.Entry

data class BitcoinChartViewEntity(
    val timeSpan: TimeSpanOption,
    val name: String,
    val description: String,
    val bitcoinPrice: String,
    val entries: List<Entry>
) {
    enum class TimeSpanOption(val stringValue: String) {

        THIRTY_DAYS("30days"),
        THREE_MONTHS("3months"),
        ONE_YEAR("1year"),
        ALL_TIME("all");
    }
}