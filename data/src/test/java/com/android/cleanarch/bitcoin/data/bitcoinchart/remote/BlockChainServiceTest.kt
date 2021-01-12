package com.android.cleanarch.bitcoin.data.bitcoinchart.remote

import BitcoinChartUtils.TIME_SPAN_30_DAYS
import com.android.cleanarch.bitcoin.data.helper.base.ApiHelperAbstract
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.ChartEntry
import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.model.BitcoinChartRaw
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException


@RunWith(JUnit4::class)
class BlockChainServiceTest : ApiHelperAbstract<BlockChainService>() {

    private lateinit var service: BlockChainService

    @Before
    fun setup() {
        service = createService(BlockChainService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchBitcoinMarketChartPriceTest() {
        val bitcoinChartResponse = BitcoinChartRaw(
            "ok",
            "Market Price (USD)",
            "USD",
            "day",
            "Average USD market price across major bitcoin exchanges.",
            arrayListOf(ChartEntry(1F, 1F))
        )

        enqueueResponse("/market_price_response")
        val testObserverResponse = service.fetchBitcoinMarketChartPrice(TIME_SPAN_30_DAYS).test()

        testObserverResponse.assertValue(bitcoinChartResponse)
        assertRequestPath("/market-price?timespan=30days")

    }
}