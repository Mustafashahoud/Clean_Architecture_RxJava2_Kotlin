package com.android.cleanarch.bitcoinapp.features.bitcoinchart.custom

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.LineData

class BitcoinChartLineChart : LineChart {

    companion object {
        private const val DEFAULT_PORT_OFFSETS = 0f
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun init() {
        super.init()
        description.isEnabled = false
        setDrawGridBackground(false)
        setDrawBorders(false)
    }

    override fun setData(data: LineData?) {
        super.setData(data)
        setViewPortOffsets(
            DEFAULT_PORT_OFFSETS,
            DEFAULT_PORT_OFFSETS,
            DEFAULT_PORT_OFFSETS,
            DEFAULT_PORT_OFFSETS
        )
        removeAxisAndGridLines(xAxis)
        removeAxisAndGridLines(axisLeft)
        removeAxisAndGridLines(axisRight)
    }

    private fun removeAxisAndGridLines(axis: AxisBase?) {
        axis?.setDrawAxisLine(false)
        axis?.setDrawGridLines(false)
    }
}