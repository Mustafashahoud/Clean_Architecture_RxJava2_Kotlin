package com.android.cleanarch.bitcoinapp.di.module

import com.android.cleanarch.bitcoinapp.di.scope.PerFeature
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.di.BitcoinChartRepositoryModule
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.di.BitcoinChartViewModelModule
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.ui.BitcoinPriceChartFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @PerFeature
    @ContributesAndroidInjector(modules = [BitcoinChartRepositoryModule::class, BitcoinChartViewModelModule::class])
    abstract fun contributeBitcoinChartFragment(): BitcoinPriceChartFragment


    // Here we add all fragments/features
}