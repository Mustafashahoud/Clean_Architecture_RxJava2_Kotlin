package com.android.cleanarch.bitcoin.data.bitcoinchart.repository

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for caching BitcoinChart.
 * This interface is a way of communicating.
 */
interface BitcoinChartCache {

    /**
     * Clear all BitcoinChart from the cache
     * @param timeSpan the timeSpan to delete a specific BitcoinChart
     */
    fun clearBitcoinChart(timeSpan: String): Completable

    /**
     * Save a given list of BitcoinChart to the cache
     * @param bitcoinChart the bitcoinChart to save
     */
    fun saveBitcoinChart(bitcoinChart: BitcoinChartEntity): Completable

    /**
     * Retrieve a BitcoinChart, from the cache
     * @param timeSpan the timeSpan to get a specific BitcoinChart
     */
    fun getBitcoinChart(timeSpan: String): Single<BitcoinChartEntity>

    /**
     * Checks if an element exists in the cache.
     * @param timeSpanKey the timeSpan to setLastCacheTime for a specific BitcoinChart
     * @return true if the element is cached, otherwise false.
     */
    fun setLastCacheTime(timeSpanKey: String, lastCache: Long)

    /**
     * Checks if the cache is expired or Does not exist yet.
     * @param timeSpanKey the timeSpan to check if a specific BitcoinChart is expired
     * @return true, the cache is expired or not in the db yet, otherwise false.
     */
    fun isCacheExpiredOrNotExist(timeSpanKey: String): Boolean

}