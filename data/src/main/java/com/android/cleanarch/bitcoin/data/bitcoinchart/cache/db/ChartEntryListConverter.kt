package com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db

import androidx.room.TypeConverter
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.ChartEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ChartEntryListConverter {
    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<ChartEntry> {
        val listType = object : TypeToken<List<ChartEntry>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<ChartEntry>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}