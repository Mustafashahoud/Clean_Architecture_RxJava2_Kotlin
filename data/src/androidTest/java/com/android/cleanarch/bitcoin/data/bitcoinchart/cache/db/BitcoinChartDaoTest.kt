package com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db

import BitcoinChartUtils.TIME_SPAN_30_DAYS
import BitcoinChartUtils.makeBitcoinChartEntity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BitcoinChartDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var bitcoinChartDao: BitcoinChartDao

    @Before
    fun setUp() {
        bitcoinChartDao = db.bitcoinChartDao()
    }

    @Test
    fun clearTablesCompletes() {
        val testObserver = bitcoinChartDao.deleteAll().test()
        testObserver.assertComplete()
    }

    @Test
    fun insertTest() {
        val bitcoinChartEntity = makeBitcoinChartEntity()
        val testObserver = bitcoinChartDao.insertBitcoinChart(bitcoinChartEntity).test()
        testObserver.assertComplete()
    }

    @Test
    fun insertAndReadTest() {

        val bitcoinChartEntity = makeBitcoinChartEntity(1, TIME_SPAN_30_DAYS)

        bitcoinChartDao.insertBitcoinChart(bitcoinChartEntity).blockingAwait()

        val testObserver = db.bitcoinChartDao().getBitcoinChart(TIME_SPAN_30_DAYS).test()

        testObserver.assertValue(bitcoinChartEntity)

    }

}