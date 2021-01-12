package com.android.cleanarch.bitcoin.data.bitcoinchart.source


import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartCache
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartDataStore
import javax.inject.Inject

/**
 * Create an instance of a BitcoinDataStore
 */
class BitcoinChartDataStoreFactory @Inject constructor(
    private val bitcoinChartCache: BitcoinChartCache,
    private val bitcoinCacheDataStore: BitcoinChartCacheDataStore,
    private val bitcoinRemoteDataStore: BitcoinChartRemoteDataStore,
) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    fun retrieveDataStore(timeSpan: String): BitcoinChartDataStore {
        if (!bitcoinChartCache.isCacheExpiredOrNotExist(timeSpan)) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Remote Data Store
     */
    fun retrieveCacheDataStore(): BitcoinChartDataStore {
        return bitcoinCacheDataStore
    }

    /**
     * Return an instance of the Cache Data Store
     */
    fun retrieveRemoteDataStore(): BitcoinChartDataStore {
        return bitcoinRemoteDataStore
    }

}