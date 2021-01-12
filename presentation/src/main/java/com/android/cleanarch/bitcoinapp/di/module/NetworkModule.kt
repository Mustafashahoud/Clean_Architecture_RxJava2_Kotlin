package com.android.cleanarch.bitcoinapp.di.module

import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.BlockChainService
import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.BlockChainServiceFactory
import com.android.cleanarch.bitcoinapp.BuildConfig
import com.android.cleanarch.bitcoinapp.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    @PerApplication
    fun provideBitcoinService(): BlockChainService {
        return BlockChainServiceFactory.createBlockChainService(BuildConfig.DEBUG)
    }
}