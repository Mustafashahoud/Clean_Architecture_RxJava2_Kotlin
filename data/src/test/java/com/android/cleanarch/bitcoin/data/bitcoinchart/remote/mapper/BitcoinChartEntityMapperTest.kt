package com.android.cleanarch.bitcoin.data.bitcoinchart.remote.mapper

import BitcoinChartUtils
import BitcoinChartUtils.TIME_SPAN_1_YEAR
import BitcoinChartUtils.TIME_SPAN_30_DAYS
import BitcoinChartUtils.TIME_SPAN_3_MONTHS
import BitcoinChartUtils.TIME_SPAN_ALL_TIME
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BitcoinChartEntityMapperTest {

    private lateinit var bitcoinChartEntityMapper: BitcoinChartEntityMapper

    @Before
    fun setUp() {
        bitcoinChartEntityMapper = BitcoinChartEntityMapper()
    }

    @Test
    fun mapFromRemoteMapsData() {
        val bitcoinChartRawOne = BitcoinChartUtils.makeBitcoinChartRaw(1)
        var bitcoinCharEntity = bitcoinChartEntityMapper.mapFromRemoteRawToEntity(bitcoinChartRawOne)

        assertThat(bitcoinChartRawOne.name, `is`(bitcoinCharEntity.name))
        assertThat(bitcoinChartRawOne.description, `is`(bitcoinCharEntity.description))
        assertThat(bitcoinChartRawOne.entries, `is`(bitcoinCharEntity.entries))
        assertThat(bitcoinCharEntity.timeSpan, `is`(TIME_SPAN_ALL_TIME))

        val bitcoinChartRaw31 = BitcoinChartUtils.makeBitcoinChartRaw(31)
        bitcoinCharEntity = bitcoinChartEntityMapper.mapFromRemoteRawToEntity(bitcoinChartRaw31)
        assertThat(bitcoinCharEntity.timeSpan, `is`(TIME_SPAN_30_DAYS))

        val bitcoinChartRaw91 = BitcoinChartUtils.makeBitcoinChartRaw(91)
        bitcoinCharEntity = bitcoinChartEntityMapper.mapFromRemoteRawToEntity(bitcoinChartRaw91)
        assertThat(bitcoinCharEntity.timeSpan, `is`(TIME_SPAN_3_MONTHS))

        val bitcoinChartRaw366 = BitcoinChartUtils.makeBitcoinChartRaw(366)
        bitcoinCharEntity = bitcoinChartEntityMapper.mapFromRemoteRawToEntity(bitcoinChartRaw366)
        assertThat(bitcoinCharEntity.timeSpan, `is`(TIME_SPAN_1_YEAR))

    }
}