package com.android.cleanarch.bitcoin.data.bitcoinchart.remote

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.mapper.BitcoinChartEntityMapper
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartRemote
import io.reactivex.Single
import javax.inject.Inject

/**
 * Remote implementation for retrieving BitcoinChart instances. This class implements the
 * [BitcoinChartRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */

class BitcoinChartRemoteImpl @Inject constructor(
    private val service: BlockChainService,
    private val entityMapper: BitcoinChartEntityMapper
) : BitcoinChartRemote {

    /**
     * Retrieve a [BitcoinChartEntity] instance from the [BlockChainService].
     */
    override fun getBitcoinChart(timeSpan: String): Single<BitcoinChartEntity> {
        return service.fetchBitcoinMarketChartPrice(timeSpan)
            .map { entityMapper.mapFromRemoteRawToEntity(it) }
    }
}