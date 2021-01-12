package com.android.cleanarch.bitcoin.data.bitcoinchart.source

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartDataStore
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartRemote
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [BitcoinChartDataStore]
 */
class BitcoinChartRemoteDataStore @Inject constructor(private val bitcoinChartRemote: BitcoinChartRemote) :
    BitcoinChartDataStore {

    override fun clearBitcoinChartData(timeSpan: String): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveBitcoinChartData(bitcoinChartEntity: BitcoinChartEntity): Completable {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a [BitcoinChartEntity] instance from the API
     */
    override fun getBitcoinChartData(timeSpan: String): Single<BitcoinChartEntity> {
        return bitcoinChartRemote.getBitcoinChart(timeSpan)
    }

}