package com.android.cleanarch.bitcoin.data.bitcoinchart.remote

import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.model.BitcoinChartRaw
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Defines the abstract methods used for interacting with the blockchain API
 */
interface BlockChainService {

    @GET("market-price")
    fun fetchBitcoinMarketChartPrice(@Query("timespan") timeSpan: String = "all"): Single<BitcoinChartRaw>
}