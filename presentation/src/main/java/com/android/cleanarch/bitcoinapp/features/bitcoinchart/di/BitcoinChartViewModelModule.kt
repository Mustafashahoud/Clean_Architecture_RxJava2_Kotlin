package com.android.cleanarch.bitcoinapp.features.bitcoinchart.di

import androidx.lifecycle.ViewModel
import com.android.cleanarch.bitcoinapp.di.ViewModelKey
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.ui.BitcoinPriceChartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class BitcoinChartViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BitcoinPriceChartViewModel::class)
    abstract fun bindBitcoinPriceChartViewModel(bitcoinPriceChartViewModel: BitcoinPriceChartViewModel): ViewModel
}