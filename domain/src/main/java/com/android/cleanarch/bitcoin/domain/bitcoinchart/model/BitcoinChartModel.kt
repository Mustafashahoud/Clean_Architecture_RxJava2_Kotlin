package com.android.cleanarch.bitcoin.domain.bitcoinchart.model

/**
 * Representation for a [BitcoinChartModel] fetched from an external layer data source
 */
data class BitcoinChartModel(
    val timeSpan: String,
    val name: String,
    val description: String,
    val entries: List<ChartEntry>
) {
    data class ChartEntry(
        val x: Float,
        val y: Float
    )
}