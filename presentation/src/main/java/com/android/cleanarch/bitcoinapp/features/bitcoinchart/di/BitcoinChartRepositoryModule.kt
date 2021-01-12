package com.android.cleanarch.bitcoinapp.features.bitcoinchart.di

import com.android.cleanarch.bitcoin.data.bitcoinchart.BitcoinChartDataRepository
import com.android.cleanarch.bitcoin.data.bitcoinchart.cache.BitcoinChartCacheImpl
import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.BitcoinChartRemoteImpl
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartCache
import com.android.cleanarch.bitcoin.data.bitcoinchart.repository.BitcoinChartRemote
import com.android.cleanarch.bitcoin.domain.bitcoinchart.repository.BitcoinChartRepository
import com.android.cleanarch.bitcoinapp.di.scope.PerFeature
import dagger.Binds
import dagger.Module


@Module
abstract class BitcoinChartRepositoryModule {

    @Binds
    @PerFeature
    abstract fun bindBitcoinChartRemote(BitcoinChartRemoteImpl: BitcoinChartRemoteImpl): BitcoinChartRemote


    @Binds
    @PerFeature
    abstract fun bindBitcoinChartCache(BitcoinChartCacheImpl: BitcoinChartCacheImpl): BitcoinChartCache

    @Binds
    @PerFeature
    abstract fun bindChartRepository(BitcoinChartDataRepository: BitcoinChartDataRepository): BitcoinChartRepository

}