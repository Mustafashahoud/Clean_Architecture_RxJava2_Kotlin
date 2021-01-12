package com.android.cleanarch.bitcoinapp.features.bitcoinchart.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorEntity
import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorHandler
import com.android.cleanarch.bitcoin.domain.bitcoinchart.interactor.GetBitcoinChart
import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import com.android.cleanarch.bitcoinapp.BitcoinChartUtils.makeBitcoinChartEntityView
import com.android.cleanarch.bitcoinapp.BitcoinChartUtils.makeBitcoinChartModel
import com.android.cleanarch.bitcoinapp.common.Resource
import com.android.cleanarch.bitcoinapp.common.Resource.Error
import com.android.cleanarch.bitcoinapp.common.Resource.Success
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.mapper.BitcoinChartViewEntityMapper
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.ui.BitcoinPriceChartViewModel
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.ui.BitcoinPriceChartViewModel.Companion.DEFAULT_TIME_SPAN
import com.android.cleanarch.bitcoinapp.base.RxSchedulerOverrideRule
import com.nhaarman.mockitokotlin2.*
import com.petertackage.kotlinoptions.optionOf
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BitcoinPriceChartViewModelTest {

    // Under test
    private lateinit var viewModel: BitcoinPriceChartViewModel

    private lateinit var interactor: GetBitcoinChart
    private lateinit var mapper: BitcoinChartViewEntityMapper
    private lateinit var errorHandler: ErrorHandler

    @get:Rule
    val schedulerRule = RxSchedulerOverrideRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        interactor = mock()
        mapper = mock()
        errorHandler = mock()
    }

    @Test
    fun testLoadData_Successful() {
        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntityView = makeBitcoinChartEntityView()
        val defaultTimeSpan: String = DEFAULT_TIME_SPAN
        val observer = mock<Observer<Resource<BitcoinChartViewEntity>>>()

        stubGetBitcoinChartUseCase(Single.just(bitcoinChartModel))
        stubBitcoinChartViewEntityMapperApply(bitcoinChartEntityView)

        viewModel = BitcoinPriceChartViewModel(interactor, mapper, errorHandler)
        viewModel.bitcoinChartLiveData.observeForever(observer)

        verify(observer).onChanged(any())
        verify(mapper).apply(bitcoinChartModel)
        verify(interactor).execute(optionOf(defaultTimeSpan))

        assertTrue(viewModel.bitcoinChartLiveData.value is Success)
        assertThat(viewModel.bitcoinChartLiveData.value?.data, `is`(bitcoinChartEntityView))
    }

    @Test
    fun testLoadData_Error() {
        val defaultTimeSpan: String = DEFAULT_TIME_SPAN
        val observer = mock<Observer<Resource<BitcoinChartViewEntity>>>()
        val throwable = mock<Throwable>()

        stubGetBitcoinChartUseCase(Single.error(throwable))
        stubErrorHandlerGetError(ErrorEntity.Unknown)

        viewModel = BitcoinPriceChartViewModel(interactor, mapper, errorHandler)
        viewModel.bitcoinChartLiveData.observeForever(observer)

        verify(observer).onChanged(any())
        verify(interactor).execute(optionOf(defaultTimeSpan))
        assertTrue(viewModel.bitcoinChartLiveData.value is Error)
        assertNull(viewModel.bitcoinChartLiveData.value?.data)
    }


    @Test
    fun testRetry() {
        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntityView = makeBitcoinChartEntityView()
        stubGetBitcoinChartUseCase(Single.just(bitcoinChartModel))
        stubBitcoinChartViewEntityMapperApply(bitcoinChartEntityView)
        viewModel = BitcoinPriceChartViewModel(interactor, mapper, errorHandler)

        viewModel.retry()

        // once when instantiating the viewModel with default timeSpan and second one when pressing retry
        verify(interactor, times(2)).execute(optionOf(DEFAULT_TIME_SPAN))
    }

    @Test
    fun testNotifyTimeSpan() {
        val bitcoinChartModel = makeBitcoinChartModel()
        val bitcoinChartEntityView = makeBitcoinChartEntityView()
        val argsCapture = argumentCaptor<String>()

        stubGetBitcoinChartUseCase(Single.just(bitcoinChartModel))
        stubBitcoinChartViewEntityMapperApply(bitcoinChartEntityView)

        viewModel = BitcoinPriceChartViewModel(interactor, mapper, errorHandler)

        val timeSpan = "1year"
        viewModel.notifyTimeSpanChanged(timeSpan)

        // once when instantiating the viewModel with DEFAULT_TIME_SPAN and the second one with 1year "pressing 1year button"
        verify(interactor, times(2)).execute(optionOf(argsCapture.capture()))

        assertThat(argsCapture.firstValue, `is`(optionOf(DEFAULT_TIME_SPAN)))
        assertThat(argsCapture.secondValue, `is`(optionOf(timeSpan)))
    }


    private fun stubGetBitcoinChartUseCase(single: Single<BitcoinChartModel>) {
        whenever(interactor.execute(any())).thenReturn(single)
    }

    private fun stubBitcoinChartViewEntityMapperApply(bitcoinChartViewEntity: BitcoinChartViewEntity) {
        whenever(mapper.apply(any())).thenReturn(bitcoinChartViewEntity)
    }

    private fun stubErrorHandlerGetError(errorEntity: ErrorEntity) {
        whenever(errorHandler.getError(any())).thenReturn(errorEntity)
    }

}