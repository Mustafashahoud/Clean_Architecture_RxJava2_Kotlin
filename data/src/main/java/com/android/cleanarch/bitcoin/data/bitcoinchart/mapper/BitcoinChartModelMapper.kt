package com.android.cleanarch.bitcoin.data.bitcoinchart.mapper

import com.android.cleanarch.bitcoin.data.base.Mapper
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.ChartEntry
import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import javax.inject.Inject

/**
 * Mapper to transfer a data layer Entity (room Entity) to domain layer model
 * Room Entities "models with "@Entity annotation" should Not be used out of their module
 */
class BitcoinChartModelMapper @Inject constructor() :
    Mapper<BitcoinChartEntity, BitcoinChartModel> {

    override fun mapFromEntity(type: BitcoinChartEntity): BitcoinChartModel {
        return BitcoinChartModel(
            name = type.name,
            description = type.description,
            timeSpan = type.timeSpan,
            entries = type.entries.map {
                BitcoinChartModel.ChartEntry(it.x, it.y)
            }
        )
    }

    override fun mapToEntity(type: BitcoinChartModel): BitcoinChartEntity {
        return BitcoinChartEntity(
            timeSpan = type.timeSpan,
            name = type.name,
            description = type.description,
            type.entries.map {
                ChartEntry(it.x, it.y)
            }
        )
    }
}