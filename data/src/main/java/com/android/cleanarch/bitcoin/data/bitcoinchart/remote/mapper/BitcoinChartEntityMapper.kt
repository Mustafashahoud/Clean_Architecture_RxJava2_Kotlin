package com.android.cleanarch.bitcoin.data.bitcoinchart.remote.mapper

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.android.cleanarch.bitcoin.data.base.EntityMapper
import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.model.BitcoinChartRaw
import javax.inject.Inject

/**
 * Map a [BitcoinChartRaw] to and from a [BitcoinChartEntity]
 */
class BitcoinChartEntityMapper @Inject constructor() :
    EntityMapper<BitcoinChartRaw, BitcoinChartEntity> {

    companion object {
        private const val THIRTY_DAYS = 31
        private const val THREE_MONTHS = 91
        private const val ONE_YEAR = 366

        private const val THIRTY_DAYS_STRING = "30days"
        private const val THREE_MONTHS_STRING = "3months"
        private const val ONE_YEAR_STRING = "1year"
        private const val ALL_TIME_STRING = "all"
    }

    /**
     * Map an instance of a [BitcoinChartRaw] to a [BitcoinChartEntity] model
     */
    override fun mapFromRemoteRawToEntity(type: BitcoinChartRaw): BitcoinChartEntity {
        return BitcoinChartEntity(
            mapTimeSpan(type.entries.size),
            type.name,
            type.description,
            type.entries
        )
    }

    private fun mapTimeSpan(entriesSize: Int): String {
        return when (entriesSize) {
            THIRTY_DAYS -> THIRTY_DAYS_STRING
            THREE_MONTHS -> THREE_MONTHS_STRING
            ONE_YEAR -> ONE_YEAR_STRING
            else -> ALL_TIME_STRING
        }
    }

}