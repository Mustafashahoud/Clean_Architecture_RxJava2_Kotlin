package com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BitcoinChartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBitcoinChart(bitcoinChartEntity: BitcoinChartEntity): Completable

    @Query("SELECT * FROM `bitcoin chart` WHERE timeSpan == :timeSpan")
    fun getBitcoinChart(timeSpan: String): Single<BitcoinChartEntity>

    @Query("DELETE FROM `bitcoin chart` WHERE timeSpan == :timeSpan")
    fun deleteBitcoinChart(timeSpan: String): Completable

    @Query("DELETE FROM `bitcoin chart`")
    fun deleteAll(): Completable

}

