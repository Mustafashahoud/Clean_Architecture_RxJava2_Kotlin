package com.android.cleanarch.bitcoin.domain.bitoinchart.interactor

import com.android.cleanarch.bitcoin.domain.bitcoinchart.interactor.GetBitcoinChart
import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import com.android.cleanarch.bitcoin.domain.bitcoinchart.repository.BitcoinChartRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.petertackage.kotlinoptions.None
import com.petertackage.kotlinoptions.optionOf
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class GetBitcoinChartTest {

    //Under test
    private lateinit var interactor: GetBitcoinChart

    private lateinit var bitcoinChartRepository: BitcoinChartRepository

    @Before
    fun setUp() {
        bitcoinChartRepository = mock()

        interactor = GetBitcoinChart(bitcoinChartRepository)
    }

    @Test(expected = IllegalStateException::class)
    fun buildUseCaseWithoutPassingTimeSpanCallsRepositoryAndThrowsException() {
        interactor.buildUseCase(None)
        verify(bitcoinChartRepository).getBitcoinChart(any())
    }

    @Test
    fun buildUseCaseReturnsData() {
        val bitcoinChartModel = mock<BitcoinChartModel>()
        val timeSpan = "30days"
        stubBitcoinChartRepositoryWithData(Single.just(bitcoinChartModel))

        val testObserver = interactor.buildUseCase(optionOf(timeSpan)).test()

        testObserver.assertValue(bitcoinChartModel)
        verify(bitcoinChartRepository).getBitcoinChart(timeSpan)
    }


    @Test
    fun buildUseCaseReturnsError() {
        val timeSpan = "30days"
        val error = mock<Throwable>()

        stubBitcoinChartRepositoryWithError(error)

        val testObserver = interactor.buildUseCase(optionOf(timeSpan)).test()

        verify(bitcoinChartRepository).getBitcoinChart(timeSpan)
        testObserver.assertNoValues()
        testObserver.assertError(error)


    }

    private fun stubBitcoinChartRepositoryWithData(single: Single<BitcoinChartModel>) {
        `when`(bitcoinChartRepository.getBitcoinChart(any())).thenReturn(single)
    }

    private fun stubBitcoinChartRepositoryWithError(error: Throwable) {
        `when`(bitcoinChartRepository.getBitcoinChart(any())).thenReturn(Single.error(error))
    }

}