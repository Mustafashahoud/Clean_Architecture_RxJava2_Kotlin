package com.android.cleanarch.bitcoinapp.features.bitcoinchart.mapper

import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity.TimeSpanOption
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity.TimeSpanOption.*
import com.android.cleanarch.bitcoinapp.util.CurrencyUtils
import com.github.mikephil.charting.data.Entry
import io.reactivex.functions.Function
import javax.inject.Inject

class BitcoinChartViewEntityMapper @Inject constructor() :
    Function<BitcoinChartModel, BitcoinChartViewEntity> {

    override fun apply(bitcoinChartModel: BitcoinChartModel): BitcoinChartViewEntity =
        BitcoinChartViewEntity(
            timeSpan = getTimeSpanOptionForString(bitcoinChartModel.timeSpan),
            name = bitcoinChartModel.name,
            description = bitcoinChartModel.description,
            bitcoinPrice = CurrencyUtils.formatAmount(bitcoinChartModel.entries.last().y),
            entries = mapChartEntries(bitcoinChartModel.entries)
        )

    private fun getTimeSpanOptionForString(timeSpan: String): TimeSpanOption =
        when (timeSpan) {
            THIRTY_DAYS.stringValue -> THIRTY_DAYS
            THREE_MONTHS.stringValue -> THREE_MONTHS
            ONE_YEAR.stringValue -> ONE_YEAR
            ALL_TIME.stringValue -> ALL_TIME
            else -> THIRTY_DAYS
        }

    private fun mapChartEntries(entries: List<BitcoinChartModel.ChartEntry>): List<Entry> =
        entries.map { Entry(it.x, it.y) }
}