package com.android.cleanarch.bitcoin.domain.bitcoinchart.repository

import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import io.reactivex.Completable
import io.reactivex.Single

interface BitcoinChartRepository {

    fun clearBitcoinChart(timeSpan: String): Completable

    fun saveBitcoinChart(bitcoinChartModel: BitcoinChartModel): Completable

    fun getBitcoinChart(timeSpan: String): Single<BitcoinChartModel>
}