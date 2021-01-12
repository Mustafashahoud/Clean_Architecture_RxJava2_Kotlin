package com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db

import BitcoinChartUtils.TIME_SPAN_30_DAYS
import BitcoinChartUtils.makeBitcoinChartEntity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.cleanarch.bitcoin.data.bitcoinchart.cache.BitcoinChartCacheImpl
import com.android.cleanarch.bitcoin.data.bitcoinchart.cache.BitcoinChartCacheImpl.Companion.EXPIRATION_TIME
import com.android.cleanarch.bitcoin.data.bitcoinchart.cache.PreferencesHelper
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.nhaarman.mockitokotlin2.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BitcoinChartCacheImplTest {

    //Under test
    private lateinit var bitcoinChartCacheImpl: BitcoinChartCacheImpl

    private lateinit var bitcoinChartDao: BitcoinChartDao
    private lateinit var preferencesHelper: PreferencesHelper

    private lateinit var timeSpanCaptor: KArgumentCaptor<String>
    private lateinit var bitcoinChartEntityCaptor: KArgumentCaptor<BitcoinChartEntity>
    private lateinit var lastCacheCaptor: KArgumentCaptor<Long>


    @Before
    fun setUp() {
        bitcoinChartDao = mock()
        preferencesHelper = mock()

        timeSpanCaptor = argumentCaptor()
        bitcoinChartEntityCaptor = argumentCaptor()
        lastCacheCaptor = argumentCaptor()

        bitcoinChartCacheImpl = BitcoinChartCacheImpl(bitcoinChartDao, preferencesHelper)
    }


    @Test
    fun clearBitcoinChartTest() {
        bitcoinChartCacheImpl.clearBitcoinChart(TIME_SPAN_30_DAYS)

        verify(bitcoinChartDao).deleteBitcoinChart(timeSpanCaptor.capture())

        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCaptor.firstValue))
    }

    @Test
    fun saveBitcoinChartTest() {
        val bitcoinChartEntity = makeBitcoinChartEntity(1, TIME_SPAN_30_DAYS)
        bitcoinChartCacheImpl.saveBitcoinChart(bitcoinChartEntity)

        verify(bitcoinChartDao).insertBitcoinChart(bitcoinChartEntityCaptor.capture())

        assertThat(bitcoinChartEntity, `is`(bitcoinChartEntityCaptor.firstValue))
    }

    @Test
    fun getBitcoinChartTest() {

        bitcoinChartCacheImpl.getBitcoinChart(TIME_SPAN_30_DAYS)

        verify(bitcoinChartDao).getBitcoinChart(timeSpanCaptor.capture())

        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCaptor.firstValue))
    }

    @Test
    fun setLastCacheTimeTest() {

        val lastCacheTime = 1000L
        bitcoinChartCacheImpl.setLastCacheTime(TIME_SPAN_30_DAYS, lastCacheTime)

        verify(preferencesHelper).setBitcoinChartLastCacheTime(
            timeSpanCaptor.capture(),
            lastCacheCaptor.capture()
        )

        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCaptor.firstValue))
        assertThat(lastCacheTime, `is`(lastCacheCaptor.firstValue))
    }


    @Test
    fun isCacheExpiredOrNotExistReturnTrueTest() {
        stubPreferencesHelperGetBitcoinChartLastCacheTime(System.currentTimeMillis() - (EXPIRATION_TIME * 2))

        val isCacheExpiredOrNotExist =
            bitcoinChartCacheImpl.isCacheExpiredOrNotExist(TIME_SPAN_30_DAYS)

        verify(preferencesHelper).getBitcoinChartLastCacheTime(timeSpanCaptor.capture())
        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCaptor.firstValue))
        assertThat(isCacheExpiredOrNotExist, `is`(true))

    }

    @Test
    fun isCacheExpiredOrNotExistReturnFalseTest() {
        stubPreferencesHelperGetBitcoinChartLastCacheTime(System.currentTimeMillis())

        val isCacheExpiredOrNotExist =
            bitcoinChartCacheImpl.isCacheExpiredOrNotExist(TIME_SPAN_30_DAYS)

        verify(preferencesHelper).getBitcoinChartLastCacheTime(timeSpanCaptor.capture())
        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCaptor.firstValue))
        assertThat(isCacheExpiredOrNotExist, `is`(false))
    }

    private fun stubPreferencesHelperGetBitcoinChartLastCacheTime(lastCache: Long) {
        whenever(preferencesHelper.getBitcoinChartLastCacheTime(any()))
            .thenReturn(lastCache)
    }
}