package com.android.cleanarch.bitcoinapp.features.bitcoinchart.custom

import android.graphics.Color
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet

class BitcoinChartLineDataSet(entries: List<Entry>, label: String) : LineDataSet(entries, label) {

    companion object {

        private const val DEFAULT_CHART_COLOR = Color.WHITE
        private const val DEFAULT_CUBIC_INTENSITY = 0.1f
        private const val DEFAULT_LINE_WIDTH = 1.6f
        private const val ALPHA_MAX = 255
        private const val DEFAULT_FILL_ALPHA = 100
    }

    init {
        mode = Mode.CUBIC_BEZIER
        cubicIntensity = DEFAULT_CUBIC_INTENSITY

        setColor(DEFAULT_CHART_COLOR, ALPHA_MAX)
        lineWidth = DEFAULT_LINE_WIDTH

        setDrawFilled(true)
        fillColor = DEFAULT_CHART_COLOR
        fillAlpha = DEFAULT_FILL_ALPHA

        setDrawCircles(false)
        setDrawValues(false)
    }
}