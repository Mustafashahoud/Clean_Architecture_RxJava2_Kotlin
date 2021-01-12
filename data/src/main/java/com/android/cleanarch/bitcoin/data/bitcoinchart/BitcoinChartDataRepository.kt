package com.android.cleanarch.bitcoin.data.bitcoinchart


import com.android.cleanarch.bitcoin.data.bitcoinchart.mapper.BitcoinChartModelMapper
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.source.BitcoinChartDataStoreFactory
import com.android.cleanarch.bitcoin.data.bitcoinchart.source.BitcoinChartRemoteDataStore
import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import com.android.cleanarch.bitcoin.domain.bitcoinchart.repository.BitcoinChartRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Provides an implementation of the [BitcoinChartRepository] interface for communicating to and from
 * data sources
 */
class BitcoinChartDataRepository @Inject constructor(
    private val factory: BitcoinChartDataStoreFactory,
    private val mapper: BitcoinChartModelMapper
) : BitcoinChartRepository {

    override fun clearBitcoinChart(timeSpan: String): Completable {
        return factory.retrieveCacheDataStore().clearBitcoinChartData(timeSpan)
    }

    override fun saveBitcoinChart(bitcoinChartModel: BitcoinChartModel): Completable {
        val bitcoinChartEntity = mapper.mapToEntity(bitcoinChartModel)
        return saveBitcoinChartEntity(bitcoinChartEntity)
    }

    private fun saveBitcoinChartEntity(bitcoinChartEntity: BitcoinChartEntity): Completable {
        return factory.retrieveCacheDataStore().saveBitcoinChartData(bitcoinChartEntity)
    }

    override fun getBitcoinChart(timeSpan: String): Single<BitcoinChartModel> {
        val dataStore = factory.retrieveDataStore(timeSpan)
        return dataStore.getBitcoinChartData(timeSpan)
            .flatMap {
                if (dataStore is BitcoinChartRemoteDataStore) {
                    saveBitcoinChartEntity(it).toSingle { it }
                } else {
                    Single.just(it)
                }
            }
            .map { bitcoinChartEntity ->
                mapper.mapFromEntity(bitcoinChartEntity)
            }
    }

}