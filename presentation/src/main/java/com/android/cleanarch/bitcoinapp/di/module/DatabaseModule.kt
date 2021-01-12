package com.android.cleanarch.bitcoinapp.di.module

import android.app.Application
import com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db.BitcoinChartDao
import com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db.BitcoinChartDb
import com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db.BitcoinChartDbFactory
import com.android.cleanarch.bitcoinapp.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @PerApplication
    fun provideBitcoinChartDb(application: Application): BitcoinChartDb {
        return BitcoinChartDbFactory.createBitcoinChartDb(application)
    }

    @Provides
    @PerApplication
    fun provideMovieDao(db: BitcoinChartDb): BitcoinChartDao {
        return db.bitcoinChartDao()
    }

}