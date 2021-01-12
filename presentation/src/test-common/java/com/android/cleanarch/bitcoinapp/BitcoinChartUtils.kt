package com.android.cleanarch.bitcoinapp

import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity
import com.github.mikephil.charting.data.Entry

object BitcoinChartUtils {

    const val TIME_SPAN_30_DAYS = "30days"

    fun makeBitcoinChartEntityView(
        entryCount: Int = 1,
        currentPrice: String = "30,036.55"
    ): BitcoinChartViewEntity {
        return BitcoinChartViewEntity(
            BitcoinChartViewEntity.TimeSpanOption.THIRTY_DAYS,
            "market",
            "Average USD market price across major bitcoin exchanges.",
            currentPrice,
            createListViewChartEntries(entryCount)
        )
    }

    fun makeBitcoinChartModel(
        entryCount: Int = 1,
        timeSpan: String = TIME_SPAN_30_DAYS
    ): BitcoinChartModel {
        return BitcoinChartModel(
            timeSpan,
            "name",
            "description",
            createListDomainChartEntries(entryCount)
        )
    }

    private fun createListViewChartEntries(count: Int): List<Entry> {
        return (0 until count).map {
            Entry(it.toFloat(), it.toFloat())
        }
    }

    private fun createListDomainChartEntries(count: Int): List<BitcoinChartModel.ChartEntry> {
        return (0 until count).map {
            BitcoinChartModel.ChartEntry(it.toFloat(), it.toFloat())
        }
    }


}