package com.android.cleanarch.bitcoin.data.bitcoinchart.repository

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to BitcoinChartEntity.
 */
interface BitcoinChartDataStore {

    fun clearBitcoinChartData(timeSpan: String): Completable

    fun saveBitcoinChartData(bitcoinChartEntity: BitcoinChartEntity): Completable

    fun getBitcoinChartData(timeSpan: String): Single<BitcoinChartEntity>

}