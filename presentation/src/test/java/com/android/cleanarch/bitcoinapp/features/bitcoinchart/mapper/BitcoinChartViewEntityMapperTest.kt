package com.android.cleanarch.bitcoinapp.features.bitcoinchart.mapper

import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel.ChartEntry
import com.android.cleanarch.bitcoinapp.BitcoinChartUtils
import com.android.cleanarch.bitcoinapp.util.CurrencyUtils
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BitcoinChartViewEntityMapperTest {

    private lateinit var bitcoinChartViewEntityMapper: BitcoinChartViewEntityMapper

    @Before
    fun setUp() {

        bitcoinChartViewEntityMapper = BitcoinChartViewEntityMapper()
    }

    @Test
    fun applyMappingFromBitcoinChartModelToBitcoinChartViewEntity() {
        val bitcoinChartModel = BitcoinChartModel(
            BitcoinChartUtils.TIME_SPAN_30_DAYS,
            "name",
            "description",
            arrayListOf(ChartEntry(1F, 1F))
        )

        val bitcoinChartViewEntity = bitcoinChartViewEntityMapper.apply(bitcoinChartModel)

        assertThat(bitcoinChartModel.name, `is`(bitcoinChartViewEntity.name))
        assertThat(bitcoinChartModel.description, `is`(bitcoinChartViewEntity.description))
        assertThat(bitcoinChartModel.entries.size, `is`(bitcoinChartViewEntity.entries.size))
        assertThat(
            CurrencyUtils.formatAmount(bitcoinChartModel.entries.last().y),
            `is`(bitcoinChartViewEntity.bitcoinPrice)
        )
        assertThat(bitcoinChartModel.entries[0].x, `is`(bitcoinChartViewEntity.entries[0].x))
        assertThat(bitcoinChartModel.entries[0].y, `is`(bitcoinChartViewEntity.entries[0].y))
        assertThat(bitcoinChartModel.timeSpan, `is`(bitcoinChartViewEntity.timeSpan.stringValue))

    }
}