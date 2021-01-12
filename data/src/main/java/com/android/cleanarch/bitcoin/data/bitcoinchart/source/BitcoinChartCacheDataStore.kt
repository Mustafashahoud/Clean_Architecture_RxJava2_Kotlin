package com.android.cleanarch.bitcoin.data.bitcoinchart.source

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartCache
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [BitcoinChartDataStore] interface.
 */
class BitcoinChartCacheDataStore @Inject constructor(private val bitcoinChartCache: BitcoinChartCache) :
    BitcoinChartDataStore {

    /**
     * Clear all Bitcoin values from the cache
     */
    override fun clearBitcoinChartData(timeSpan: String): Completable {
        return bitcoinChartCache.clearBitcoinChart(timeSpan)
    }

    override fun saveBitcoinChartData(bitcoinChartEntity: BitcoinChartEntity): Completable {
        return bitcoinChartCache.saveBitcoinChart(bitcoinChartEntity)
            .doOnComplete {
                bitcoinChartCache.setLastCacheTime(bitcoinChartEntity.timeSpan, System.currentTimeMillis())
            }
    }


    /**
     * Retrieve BitcoinChart instance from the cache
     */
    override fun getBitcoinChartData(timeSpan: String): Single<BitcoinChartEntity> {
        return bitcoinChartCache.getBitcoinChart(timeSpan)
    }

}