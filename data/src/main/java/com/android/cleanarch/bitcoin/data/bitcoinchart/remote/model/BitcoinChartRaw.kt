package com.android.cleanarch.bitcoin.data.bitcoinchart.remote.model

import com.android.cleanarch.bitcoin.data.bitcoinchart.model.ChartEntry
import com.google.gson.annotations.SerializedName


data class BitcoinChartRaw(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    @SerializedName("values")
    val entries: List<ChartEntry>
)
