package com.android.cleanarch.bitcoin.data.bitcoinchart.remote

import BitcoinChartUtils
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import BitcoinChartUtils.TIME_SPAN_1_YEAR
import BitcoinChartUtils.TIME_SPAN_30_DAYS
import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.mapper.BitcoinChartEntityMapper
import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.model.BitcoinChartRaw
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`


@RunWith(JUnit4::class)
class BitcoinChartRemoteImplTest {

    private lateinit var entityMapper: BitcoinChartEntityMapper
    private lateinit var blockChainService: BlockChainService

    private lateinit var bitcoinChartRemoteImpl: BitcoinChartRemoteImpl

    private val timeSpanCaptor = argumentCaptor<String>()

    @Before
    fun setup() {
        entityMapper = mock()
        blockChainService = mock()
        bitcoinChartRemoteImpl = BitcoinChartRemoteImpl(blockChainService, entityMapper)
    }


    @Test
    fun `Block chain API should be requested with the same value that BitcoinChartRemoteImpl is requested for`() {
        val bitcoinChartRaw = BitcoinChartUtils.makeBitcoinChartRaw()

        stubBlockChainServiceWithData(Single.just(bitcoinChartRaw))

        bitcoinChartRemoteImpl.getBitcoinChart(TIME_SPAN_1_YEAR)

        verify(blockChainService).fetchBitcoinMarketChartPrice(timeSpanCaptor.capture())
        assertThat(TIME_SPAN_1_YEAR, `is`(timeSpanCaptor.firstValue))
    }


    @Test
    fun getBitcoinChartReturnsDataTest() {
        val bitcoinChartRaw = BitcoinChartUtils.makeBitcoinChartRaw()
        val bitcoinChartEntity = BitcoinChartUtils.makeBitcoinChartEntity()


        stubBlockChainServiceWithData(Single.just(bitcoinChartRaw))
        stubEntityMapperMapFromRemoteRawToEntity(bitcoinChartEntity)

        val testObserver = bitcoinChartRemoteImpl.getBitcoinChart(TIME_SPAN_30_DAYS).test()

        testObserver.assertValue(bitcoinChartEntity)

    }

    @Test
    fun getBitcoinChartReturnsErrorTest() {
        val throwable: Throwable = mock()

        stubBlockChainServiceWithError(throwable)

        val testObserver = bitcoinChartRemoteImpl.getBitcoinChart(TIME_SPAN_30_DAYS).test()

        testObserver.assertError(throwable)
        testObserver.assertNoValues()

    }

    private fun stubBlockChainServiceWithData(single: Single<BitcoinChartRaw>) {
        `when`(blockChainService.fetchBitcoinMarketChartPrice(any()))
            .thenReturn(single)
    }

    private fun stubBlockChainServiceWithError(error: Throwable) {
        `when`(blockChainService.fetchBitcoinMarketChartPrice(any()))
            .thenReturn(Single.error(error))
    }

    private fun stubEntityMapperMapFromRemoteRawToEntity(bitcoinChartEntity: BitcoinChartEntity) {
        `when`(entityMapper.mapFromRemoteRawToEntity(any()))
            .thenReturn(bitcoinChartEntity)
    }


}