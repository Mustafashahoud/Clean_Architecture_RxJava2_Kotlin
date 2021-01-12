package com.android.cleanarch.bitcoinapp.features.bitcoinchart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorHandler
import com.android.cleanarch.bitcoin.domain.bitcoinchart.interactor.GetBitcoinChart
import com.android.cleanarch.bitcoinapp.common.Resource
import com.android.cleanarch.bitcoinapp.common.Resource.*
import com.android.cleanarch.bitcoinapp.common.ViewModelBase
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.mapper.BitcoinChartViewEntityMapper
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity.TimeSpanOption.THIRTY_DAYS
import com.android.cleanarch.bitcoinapp.testing.OpenForTesting
import com.petertackage.kotlinoptions.optionOf
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@OpenForTesting
class BitcoinPriceChartViewModel @Inject constructor(
    private val getBitcoinChart: GetBitcoinChart,
    private val bitcoinChartViewEntityMapper: BitcoinChartViewEntityMapper,
    errorHandler: ErrorHandler
) : ViewModelBase(errorHandler) {

    companion object {
        val DEFAULT_TIME_SPAN = THIRTY_DAYS.stringValue
    }

    private val timeSpanBehaviorSubject = BehaviorSubject.create<String>()

    private val _bitcoinChartLiveData = MutableLiveData<Resource<BitcoinChartViewEntity>>()
    val bitcoinChartLiveData: LiveData<Resource<BitcoinChartViewEntity>> get() = _bitcoinChartLiveData

    init {
        addToDisposable(getBitcoinChartStream(DEFAULT_TIME_SPAN))
    }


    private fun getBitcoinChartStream(timeSpan: String): Disposable {
        return timeSpanBehaviorSubject
            .startWith(timeSpan)
            .doOnNext { handleLoading() }
            .switchMap {
                getBitcoinChart.execute(optionOf(it))
                    .toObservable()
            }.observeOn(Schedulers.computation())
            .map(bitcoinChartViewEntityMapper)
            .subscribe(
                { data -> handleSuccess(data) },
                { error -> handleError(error) }
            )
    }

    private fun handleLoading() {
        _bitcoinChartLiveData.postValue(Loading())
    }

    private fun handleSuccess(data: BitcoinChartViewEntity) {
        _bitcoinChartLiveData.postValue(Success(data))
    }

    private fun handleError(error: Throwable?) {
        _bitcoinChartLiveData.postValue(Error(getErrorType(error)))
    }

    fun notifyTimeSpanChanged(timeSpan: String) {
        timeSpanBehaviorSubject.onNext(timeSpan)
    }

    fun retry() {
        timeSpanBehaviorSubject.value?.let {
            getBitcoinChartStream(it)
        } ?: getBitcoinChartStream(DEFAULT_TIME_SPAN)
    }

}