package com.android.cleanarch.bitcoin.data.bitcoinchart.mapper

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.ChartEntry
import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class BitcoinChartModelMapperTest {

    private lateinit var bitcoinChartModelMapper: BitcoinChartModelMapper

    @Before
    fun setUp() {
        bitcoinChartModelMapper = BitcoinChartModelMapper()
    }

    @Test
    fun `test mapFromEntity it should have a BitcoinChartEntity as an input and then return BitcoinChartModel`() {
        val bitcoinChartEntity = BitcoinChartEntity(
            "30days", "name", "description", arrayListOf(
                ChartEntry(1F, 1F)
            )
        )
        val bitcoinChartModel = bitcoinChartModelMapper.mapFromEntity(bitcoinChartEntity)

        assertThat(bitcoinChartEntity.name, `is`(bitcoinChartModel.name))
        assertThat(bitcoinChartEntity.description, `is`(bitcoinChartModel.description))
        assertThat(bitcoinChartEntity.timeSpan, `is`(bitcoinChartModel.timeSpan))
        assertThat(bitcoinChartEntity.entries.size, `is`(bitcoinChartModel.entries.size))
        assertThat(bitcoinChartEntity.entries[0].x, `is`(bitcoinChartModel.entries[0].x))
        assertThat(bitcoinChartEntity.entries[0].y, `is`(bitcoinChartModel.entries[0].y))
    }

    @Test
    fun `test mapToEntity it should have a BitcoinChartModel as an input and then return BitcoinChartEntity`() {
        val bitcoinChartEntity = BitcoinChartModel(
            "name", "description", "30days", arrayListOf(
                BitcoinChartModel.ChartEntry(1F, 1F)
            )
        )
        val bitcoinChartModel = bitcoinChartModelMapper.mapToEntity(bitcoinChartEntity)

        assertThat(bitcoinChartModel.name, `is`(bitcoinChartEntity.name))
        assertThat(bitcoinChartModel.description, `is`(bitcoinChartEntity.description))
        assertThat(bitcoinChartModel.timeSpan, `is`(bitcoinChartEntity.timeSpan))
        assertThat(bitcoinChartModel.entries.size, `is`(bitcoinChartEntity.entries.size))
        assertThat(bitcoinChartModel.entries[0].x, `is`(bitcoinChartEntity.entries[0].x))
        assertThat(bitcoinChartModel.entries[0].y, `is`(bitcoinChartEntity.entries[0].y))
    }
}