package com.android.cleanarch.bitcoin.data.bitcoinchart.source

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import BitcoinChartUtils.TIME_SPAN_30_DAYS
import BitcoinChartUtils.makeBitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartCache
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class BitcoinChartCacheDataStoreTest {

    //Under test
    private lateinit var bitcoinChartCacheDataStore: BitcoinChartCacheDataStore

    private lateinit var bitcoinChartCache: BitcoinChartCache
    private lateinit var timeSpanCapture: KArgumentCaptor<String>
    private lateinit var bitcoinCharEntityCapture: KArgumentCaptor<BitcoinChartEntity>

    @Before
    fun setUp() {
        bitcoinChartCache = mock()
        timeSpanCapture = argumentCaptor()
        bitcoinCharEntityCapture = argumentCaptor()
        bitcoinChartCacheDataStore = BitcoinChartCacheDataStore(bitcoinChartCache)
    }

    @Test
    fun clearBitcoinChartDataTest() {
        `when`(bitcoinChartCache.clearBitcoinChart(any()))
            .thenReturn(Completable.complete())

        val testObserver =
            bitcoinChartCacheDataStore.clearBitcoinChartData(TIME_SPAN_30_DAYS).test()

        verify(bitcoinChartCache).clearBitcoinChart(timeSpanCapture.capture())

        testObserver.assertComplete()
        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.firstValue))
    }

    @Test
    fun saveBitcoinChartDataTest() {
        `when`(bitcoinChartCache.saveBitcoinChart(any()))
            .thenReturn(Completable.complete())

        val bitcoinCharEntity = makeBitcoinChartEntity(1, TIME_SPAN_30_DAYS)

        val testObserver =
            bitcoinChartCacheDataStore.saveBitcoinChartData(bitcoinCharEntity).test()

        testObserver.assertComplete()

        verify(bitcoinChartCache).saveBitcoinChart(bitcoinCharEntityCapture.capture())
        verify(bitcoinChartCache).setLastCacheTime(any(), any())

        assertThat(bitcoinCharEntity, `is`(bitcoinCharEntityCapture.firstValue))
    }


    @Test
    fun getBitcoinChartDataTest() {
        val bitcoinChartEntity = makeBitcoinChartEntity(1, TIME_SPAN_30_DAYS)

        `when`(bitcoinChartCache.getBitcoinChart(any()))
            .thenReturn(Single.just(bitcoinChartEntity))

        val testObserver =
            bitcoinChartCacheDataStore.getBitcoinChartData(TIME_SPAN_30_DAYS).test()

        testObserver.assertValue(bitcoinChartEntity)

        verify(bitcoinChartCache).getBitcoinChart(timeSpanCapture.capture())

        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.firstValue))
    }
}