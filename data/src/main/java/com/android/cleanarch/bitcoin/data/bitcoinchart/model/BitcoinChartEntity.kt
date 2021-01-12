package com.android.cleanarch.bitcoin.data.bitcoinchart.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bitcoin chart")
data class BitcoinChartEntity(
    @PrimaryKey
    val timeSpan: String,
    val name: String,
    val description: String,
    val entries: List<ChartEntry>
)