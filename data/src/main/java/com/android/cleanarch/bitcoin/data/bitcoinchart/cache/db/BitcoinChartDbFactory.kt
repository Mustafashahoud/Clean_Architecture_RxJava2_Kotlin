package com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db

import android.app.Application
import androidx.room.Room


/**
 * Provide "create" method to create instance of [BitcoinChartDb]
 */
object BitcoinChartDbFactory {

    fun createBitcoinChartDb(application: Application): BitcoinChartDb {
        return Room
            .databaseBuilder(application, BitcoinChartDb::class.java, "BitcoinChart.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}