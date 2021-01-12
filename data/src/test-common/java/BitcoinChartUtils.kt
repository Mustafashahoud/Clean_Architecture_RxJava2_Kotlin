import com.android.cleanarch.bitcoin.data.bitcoinchart.model.BitcoinChartEntity
import com.android.cleanarch.bitcoin.data.bitcoinchart.model.ChartEntry
import com.android.cleanarch.bitcoin.data.bitcoinchart.remote.model.BitcoinChartRaw
import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel

object BitcoinChartUtils {

    const val TIME_SPAN_30_DAYS = "30days"
    const val TIME_SPAN_3_MONTHS = "3months"
    const val TIME_SPAN_1_YEAR = "1year"
    const val TIME_SPAN_ALL_TIME = "all"

    fun makeBitcoinChartRaw(entryCount: Int = 1): BitcoinChartRaw {
        return BitcoinChartRaw(
            "status",
            "market",
            "usd", "day",
            "description",
            createListDataChartEntries(entryCount)
        )
    }

    fun makeBitcoinChartEntity(
        entryCount: Int = 1,
        timeSpan: String = TIME_SPAN_30_DAYS
    ): BitcoinChartEntity {
        return BitcoinChartEntity(
            timeSpan,
            "name",
            "description",
            createListDataChartEntries(entryCount)
        )
    }

    fun makeBitcoinChartModel(
        entryCount: Int = 1,
        timeSpan: String = TIME_SPAN_30_DAYS
    ): BitcoinChartModel {
        return BitcoinChartModel(
            timeSpan,
            "name",
            "description",
            createListDomainChartEntries(entryCount)
        )
    }

    private fun createListDataChartEntries(count: Int): List<ChartEntry> {
        return (0 until count).map {
            ChartEntry(it.toFloat(), it.toFloat())
        }
    }

    private fun createListDomainChartEntries(count: Int): List<BitcoinChartModel.ChartEntry> {
        return (0 until count).map {
            BitcoinChartModel.ChartEntry(it.toFloat(), it.toFloat())
        }
    }


}