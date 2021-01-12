package com.android.cleanarch.bitcoin.data.bitcoinchart.repository

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import io.reactivex.Single

/**
 * Interface defining methods to fetch a BitcoinChart.
 * This interface is a way of communicating.
 */
interface BitcoinChartRemote {

    /**
     * fetch a BitcoinChart
     */
    fun getBitcoinChart(timeSpan: String): Single<BitcoinChartEntity>

}