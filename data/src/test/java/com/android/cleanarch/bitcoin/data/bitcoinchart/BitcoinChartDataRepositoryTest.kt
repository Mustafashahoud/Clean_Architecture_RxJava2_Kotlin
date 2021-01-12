package com.android.cleanarch.bitcoin.data.bitcoinchart

import com.android.cleanarch.bitcoin.data.bitcoinchart.mapper.BitcoinChartModelMapper
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import BitcoinChartUtils.TIME_SPAN_30_DAYS
import BitcoinChartUtils.makeBitcoinChartEntity
import BitcoinChartUtils.makeBitcoinChartModel
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartDataStore
import com.android.cleanarch.bitcoin.data.bitcoinchart.source.BitcoinChartCacheDataStore
import com.android.cleanarch.bitcoin.data.bitcoinchart.source.BitcoinChartDataStoreFactory
import com.android.cleanarch.bitcoin.data.bitcoinchart.source.BitcoinChartRemoteDataStore
import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
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
class BitcoinChartDataRepositoryTest {

    //Under test
    private lateinit var bitcoinChartDataRepository: BitcoinChartDataRepository

    private lateinit var bitcoinChartDataStoreFactory: BitcoinChartDataStoreFactory
    private lateinit var bitcoinChartModelMapper: BitcoinChartModelMapper

    private lateinit var bitcoinCacheDataStore: BitcoinChartCacheDataStore
    private lateinit var bitcoinRemoteDataStore: BitcoinChartRemoteDataStore

    private lateinit var timeSpanCapture: KArgumentCaptor<String>
    private lateinit var bitcoinChartModelCapture: KArgumentCaptor<BitcoinChartModel>
    private lateinit var bitcoinChartEntityCapture: KArgumentCaptor<BitcoinChartEntity>

    @Before
    fun setUp() {

        timeSpanCapture = argumentCaptor()
        bitcoinChartModelCapture = argumentCaptor()
        bitcoinChartEntityCapture = argumentCaptor()

        bitcoinCacheDataStore = mock()
        bitcoinRemoteDataStore = mock()

        bitcoinChartDataStoreFactory = mock()
        bitcoinChartModelMapper = mock()

        bitcoinChartDataRepository =
            BitcoinChartDataRepository(bitcoinChartDataStoreFactory, bitcoinChartModelMapper)

        stubBitcoinChartDataStoreFactoryRetrieveCacheDataStore()
        stubBitcoinChartDataStoreFactoryRetrieveRemoteDataStore()
    }

    @Test
    fun clearBitcoinChartCompletes() {
        stubBitcoinChartCacheClearBitcoinChart(Completable.complete())

        val testObserver = bitcoinChartDataRepository.clearBitcoinChart(TIME_SPAN_30_DAYS).test()

        testObserver.assertComplete()
    }

    @Test
    fun clearBitcoinChartCallsCacheDataStoreWithSameArgs() {
        stubBitcoinChartCacheClearBitcoinChart(Completable.complete())

        bitcoinChartDataRepository.clearBitcoinChart(TIME_SPAN_30_DAYS).test()

        verify(bitcoinChartDataStoreFactory).retrieveCacheDataStore()
        verify(bitcoinCacheDataStore).clearBitcoinChartData(timeSpanCapture.capture())

        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.firstValue))
    }

    @Test
    fun clearBitcoinChartNeverCallsRemoteDataStore() {
        stubBitcoinChartCacheClearBitcoinChart(Completable.complete())

        bitcoinChartDataRepository.clearBitcoinChart(TIME_SPAN_30_DAYS).test()

        verify(bitcoinRemoteDataStore, never()).clearBitcoinChartData(any())

    }


    @Test
    fun saveBitcoinChartCompletes() {
        stubBitcoinChartCacheSaveBitcoinChart(Completable.complete())

        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntity = makeBitcoinChartEntity()

        stubBitcoinChartMapperMapToEntity(bitcoinChartModel, bitcoinChartEntity)

        val testObserver = bitcoinChartDataRepository.saveBitcoinChart(bitcoinChartModel).test()

        testObserver.assertComplete()
    }

    @Test
    fun saveBitcoinChartCallsCacheDataStoreWithSameArgs() {
        stubBitcoinChartCacheSaveBitcoinChart(Completable.complete())

        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntity = makeBitcoinChartEntity()

        stubBitcoinChartMapperMapToEntity(bitcoinChartModel, bitcoinChartEntity)

        bitcoinChartDataRepository.saveBitcoinChart(bitcoinChartModel).test()

        verify(bitcoinChartModelMapper).mapToEntity(bitcoinChartModelCapture.capture())
        verify(bitcoinChartDataStoreFactory).retrieveCacheDataStore()
        verify(bitcoinCacheDataStore).saveBitcoinChartData(bitcoinChartEntityCapture.capture())

        assertThat(bitcoinChartEntity, `is`(bitcoinChartEntityCapture.firstValue))
        assertThat(bitcoinChartModel, `is`(bitcoinChartModelCapture.firstValue))
    }

    @Test
    fun saveBitcoinChartNeverCallsRemoteDataStore() {
        stubBitcoinChartCacheSaveBitcoinChart(Completable.complete())

        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntity = makeBitcoinChartEntity()

        stubBitcoinChartMapperMapToEntity(bitcoinChartModel, bitcoinChartEntity)

        bitcoinChartDataRepository.saveBitcoinChart(bitcoinChartModel).test()

        verify(bitcoinRemoteDataStore, never()).clearBitcoinChartData(any())

    }


    @Test
    fun getBitcoinChartCompletes() {
        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntity = makeBitcoinChartEntity()

        stubBitcoinChartDataStoreFactoryRetrieveDataStore(bitcoinCacheDataStore)
        stubBitcoinChartCacheDataStoreGetBitcoinChart(Single.just(bitcoinChartEntity))
        stubBitcoinChartMapperMapFromEntity(bitcoinChartEntity, bitcoinChartModel)

        val testObserver = bitcoinChartDataRepository.getBitcoinChart(TIME_SPAN_30_DAYS).test()

        testObserver.assertComplete()
    }

    @Test
    fun getBitcoinChartReturnsDataFromCacheDataSource() {
        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntity = makeBitcoinChartEntity()

        stubBitcoinChartDataStoreFactoryRetrieveDataStore(bitcoinCacheDataStore)
        stubBitcoinChartCacheDataStoreGetBitcoinChart(Single.just(bitcoinChartEntity))
        stubBitcoinChartMapperMapFromEntity(bitcoinChartEntity, bitcoinChartModel)

        val testObserver = bitcoinChartDataRepository.getBitcoinChart(TIME_SPAN_30_DAYS).test()
        testObserver.assertValue(bitcoinChartModel)

        verify(bitcoinChartDataStoreFactory).retrieveDataStore(timeSpanCapture.capture())
        verify(bitcoinCacheDataStore).getBitcoinChartData(timeSpanCapture.capture())
        verify(bitcoinChartModelMapper).mapFromEntity(bitcoinChartEntityCapture.capture())

        assertThat(bitcoinChartEntity, `is`(bitcoinChartEntityCapture.firstValue))
        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.firstValue))
        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.secondValue))
    }

    @Test
    fun getBitcoinChartReturnsDataFromRemoteDataSource() {
        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntity = makeBitcoinChartEntity()

        stubBitcoinChartDataStoreFactoryRetrieveDataStore(bitcoinRemoteDataStore)
        stubBitcoinChartRemoteDataStoreGetBitcoinChart(Single.just(bitcoinChartEntity))
        stubBitcoinChartCacheSaveBitcoinChart(Completable.complete())
        stubBitcoinChartMapperMapFromEntity(bitcoinChartEntity, bitcoinChartModel)

        val testObserver = bitcoinChartDataRepository.getBitcoinChart(TIME_SPAN_30_DAYS).test()
        testObserver.assertValue(bitcoinChartModel)

        verify(bitcoinChartDataStoreFactory).retrieveDataStore(timeSpanCapture.capture())
        verify(bitcoinRemoteDataStore).getBitcoinChartData(timeSpanCapture.capture())
        verify(bitcoinCacheDataStore).saveBitcoinChartData(bitcoinChartEntityCapture.capture())
        verify(bitcoinChartModelMapper).mapFromEntity(bitcoinChartEntityCapture.capture())

        assertThat(bitcoinChartEntity, `is`(bitcoinChartEntityCapture.firstValue))
        assertThat(bitcoinChartEntity, `is`(bitcoinChartEntityCapture.secondValue))
        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.firstValue))
        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.secondValue))
    }


    @Test
    fun getBitcoinChartThrowsErrorFromRemoteDataSource() {
        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntity = makeBitcoinChartEntity()
        val throwable: Throwable = mock()

        stubBitcoinChartDataStoreFactoryRetrieveDataStore(bitcoinRemoteDataStore)
        stubBitcoinChartRemoteDataStoreGetBitcoinChartWithError(throwable)
        stubBitcoinChartCacheSaveBitcoinChart(Completable.complete())
        stubBitcoinChartMapperMapFromEntity(bitcoinChartEntity, bitcoinChartModel)

        val testObserver = bitcoinChartDataRepository.getBitcoinChart(TIME_SPAN_30_DAYS).test()
        testObserver.assertNoValues()
        testObserver.assertError(throwable)

        verify(bitcoinChartDataStoreFactory).retrieveDataStore(timeSpanCapture.capture())
        verify(bitcoinRemoteDataStore).getBitcoinChartData(timeSpanCapture.capture())

        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.firstValue))
        assertThat(TIME_SPAN_30_DAYS, `is`(timeSpanCapture.secondValue))
    }


    private fun stubBitcoinChartDataStoreFactoryRetrieveDataStore(dataStore: BitcoinChartDataStore) {
        `when`(bitcoinChartDataStoreFactory.retrieveDataStore(any()))
            .thenReturn(dataStore)
    }

    private fun stubBitcoinChartDataStoreFactoryRetrieveCacheDataStore() {
        `when`(bitcoinChartDataStoreFactory.retrieveCacheDataStore())
            .thenReturn(bitcoinCacheDataStore)
    }

    private fun stubBitcoinChartDataStoreFactoryRetrieveRemoteDataStore() {
        `when`(bitcoinChartDataStoreFactory.retrieveRemoteDataStore())
            .thenReturn(bitcoinRemoteDataStore)
    }

    private fun stubBitcoinChartCacheClearBitcoinChart(completable: Completable) {
        `when`(bitcoinCacheDataStore.clearBitcoinChartData(any()))
            .thenReturn(completable)
    }

    private fun stubBitcoinChartCacheSaveBitcoinChart(completable: Completable) {
        `when`(bitcoinCacheDataStore.saveBitcoinChartData(any()))
            .thenReturn(completable)
    }

    private fun stubBitcoinChartRemoteDataStoreGetBitcoinChart(single: Single<BitcoinChartEntity>) {
        `when`(bitcoinRemoteDataStore.getBitcoinChartData(any()))
            .thenReturn(single)
    }

    private fun stubBitcoinChartRemoteDataStoreGetBitcoinChartWithError(error: Throwable) {
        `when`(bitcoinRemoteDataStore.getBitcoinChartData(any()))
            .thenReturn(Single.error(error))
    }

    private fun stubBitcoinChartCacheDataStoreGetBitcoinChart(single: Single<BitcoinChartEntity>) {
        `when`(bitcoinCacheDataStore.getBitcoinChartData(any()))
            .thenReturn(single)
    }

    private fun stubBitcoinChartMapperMapFromEntity(
        bitcoinChartEntity: BitcoinChartEntity,
        bitcoinChartModel: BitcoinChartModel
    ) {
        `when`(bitcoinChartModelMapper.mapFromEntity(bitcoinChartEntity))
            .thenReturn(bitcoinChartModel)
    }

    private fun stubBitcoinChartMapperMapToEntity(
        bitcoinChartModel: BitcoinChartModel,
        bitcoinChartEntity: BitcoinChartEntity
    ) {
        `when`(bitcoinChartModelMapper.mapToEntity(bitcoinChartModel))
            .thenReturn(bitcoinChartEntity)
    }
}