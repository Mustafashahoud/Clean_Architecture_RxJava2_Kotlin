package com.android.cleanarch.bitcoin.data.bitcoinchart.source

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import BitcoinChartUtils.TIME_SPAN_30_DAYS
import BitcoinChartUtils.makeBitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartRemote
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class BitcoinChartRemoteDataStoreTest {

    //Under test
    private lateinit var bitcoinChartRemoteDataStore: BitcoinChartRemoteDataStore

    private lateinit var bitcoinChartRemote: BitcoinChartRemote
    private lateinit var timeSpanCapture: KArgumentCaptor<String>

    @Before
    fun setUp() {
        bitcoinChartRemote = mock()
        timeSpanCapture = argumentCaptor()
        bitcoinChartRemoteDataStore = BitcoinChartRemoteDataStore(bitcoinChartRemote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearBitcoinChartDataTest() {
        bitcoinChartRemoteDataStore.clearBitcoinChartData(TIME_SPAN_30_DAYS).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveBitcoinChartDataTest() {
        bitcoinChartRemoteDataStore.saveBitcoinChartData(makeBitcoinChartEntity(1))
            .test()
    }

    @Test
    fun getBitcoinChartDataReturnsDataTest() {

        val bitcoinChartEntity = makeBitcoinChartEntity(1, TIME_SPAN_30_DAYS)

        stubBitcoinChartRemoteReturnsData(Single.just(bitcoinChartEntity))

        val testObserver = bitcoinChartRemoteDataStore.getBitcoinChartData(TIME_SPAN_30_DAYS).test()

        testObserver.assertValue(bitcoinChartEntity)

        verify(bitcoinChartRemote).getBitcoinChart(timeSpanCapture.capture())

        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.firstValue))
    }

    @Test
    fun getBitcoinChartDataReturnsErrorTest() {

        val throwable = mock<Throwable>()

        stubBitcoinChartRemoteReturnsError(throwable)

        val testObserver = bitcoinChartRemoteDataStore.getBitcoinChartData(TIME_SPAN_30_DAYS).test()

        testObserver.assertNoValues()
        testObserver.assertError(throwable)

        verify(bitcoinChartRemote).getBitcoinChart(timeSpanCapture.capture())

        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.firstValue))
    }

    private fun stubBitcoinChartRemoteReturnsData(single: Single<BitcoinChartEntity>) {
        `when`(bitcoinChartRemote.getBitcoinChart(any()))
            .thenReturn(single)
    }

    private fun stubBitcoinChartRemoteReturnsError(error: Throwable) {
        `when`(bitcoinChartRemote.getBitcoinChart(any()))
            .thenReturn(Single.error(error))
    }
}