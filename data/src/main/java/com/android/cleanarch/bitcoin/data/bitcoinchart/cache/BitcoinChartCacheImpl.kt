package com.android.cleanarch.bitcoin.data.bitcoinchart.cache

import com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db.BitcoinChartDao
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of [BitcoinChartCache], any other reactive caching mechanism can be used.
 */
class BitcoinChartCacheImpl @Inject constructor(
    private val dao: BitcoinChartDao,
    private val preferencesHelper: PreferencesHelper
) : BitcoinChartCache {


    companion object {
        const val EXPIRATION_TIME = (60 * 10 * 1000).toLong() // 5 minutes
    }

    override fun clearBitcoinChart(timeSpan: String): Completable {
        return dao.deleteBitcoinChart(timeSpan)
    }

    override fun saveBitcoinChart(bitcoinChart: BitcoinChartEntity): Completable {
        return dao.insertBitcoinChart(bitcoinChart)
    }

    override fun getBitcoinChart(timeSpan: String): Single<BitcoinChartEntity> {
        return dao.getBitcoinChart(timeSpan)
    }

    override fun setLastCacheTime(timeSpanKey: String, lastCache: Long) {
        preferencesHelper.setBitcoinChartLastCacheTime(timeSpanKey, lastCache)
    }

    override fun isCacheExpiredOrNotExist(timeSpanKey: String): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = preferencesHelper.getBitcoinChartLastCacheTime(timeSpanKey)

        // No cache for a chart with this timeSpan key
        if (lastUpdateTime == PreferencesHelper.DEFAULT_VALUE) {
            return true
        }
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }


}