package com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity

@Database(
    entities = [BitcoinChartEntity::class],
    version = 4, exportSchema = false
)
@TypeConverters(
    value = [(ChartEntryListConverter::class)]
)
abstract class BitcoinChartDb : RoomDatabase(){
    abstract fun bitcoinChartDao(): BitcoinChartDao
}