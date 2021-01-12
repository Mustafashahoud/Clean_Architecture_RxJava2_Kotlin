package com.android.cleanarch.bitcoin.data.bitcoinchart.source

import BitcoinChartUtils.TIME_SPAN_30_DAYS
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartCache
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class BitcoinChartDataStoreFactoryTest {

    // Under test
    private lateinit var bitcoinChartDataStoreFactory: BitcoinChartDataStoreFactory

    private lateinit var bitcoinChartCache: BitcoinChartCache
    private lateinit var bitcoinCacheDataStore: BitcoinChartCacheDataStore
    private lateinit var bitcoinRemoteDataStore: BitcoinChartRemoteDataStore

    @Before
    fun setUp() {
        bitcoinChartCache = mock()
        bitcoinCacheDataStore = mock()
        bitcoinRemoteDataStore = mock()

        bitcoinChartDataStoreFactory = BitcoinChartDataStoreFactory(
            bitcoinChartCache,
            bitcoinCacheDataStore,
            bitcoinRemoteDataStore
        )
    }

    @Test
    fun retrieveDataStoreWhenCachedAndNotExpiredReturnsCacheDataStore() {
        stubBitcoinChartIsCacheExpiredOrNotExist(false)
        val bitcoinChartSource = bitcoinChartDataStoreFactory.retrieveDataStore(TIME_SPAN_30_DAYS)
        assert(bitcoinChartSource is BitcoinChartCacheDataStore)

    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubBitcoinChartIsCacheExpiredOrNotExist(true)
        val bitcoinChartSource = bitcoinChartDataStoreFactory.retrieveDataStore(TIME_SPAN_30_DAYS)
        assert(bitcoinChartSource is BitcoinChartRemoteDataStore)
    }

    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val bitcoinChartSource = bitcoinChartDataStoreFactory.retrieveRemoteDataStore()
        assert(bitcoinChartSource is BitcoinChartRemoteDataStore)
    }

    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val bitcoinChartSource = bitcoinChartDataStoreFactory.retrieveCacheDataStore()
        assert(bitcoinChartSource is BitcoinChartCacheDataStore)
    }


    private fun stubBitcoinChartIsCacheExpiredOrNotExist(isCachedAndNotExpired: Boolean) {
        `when`(bitcoinChartCache.isCacheExpiredOrNotExist(any()))
            .thenReturn(isCachedAndNotExpired)
    }


}