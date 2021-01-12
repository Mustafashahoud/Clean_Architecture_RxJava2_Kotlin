package com.android.cleanarch.bitcoinapp.features.bitcoinchart.binidng

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.cleanarch.bitcoinapp.R
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.custom.BitcoinChartLineDataSet
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.custom.CustomMarkerView
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData

object BindingAdapters {

    @BindingAdapter("setChartData")
    @JvmStatic
    fun bindChart(view: LineChart, bitcoinChartData: BitcoinChartViewEntity?) {

        if (bitcoinChartData?.entries == null || bitcoinChartData.entries.isEmpty()) {
            return
        }

        val dataSet = BitcoinChartLineDataSet(bitcoinChartData.entries, bitcoinChartData.name)
        val lineData = LineData(dataSet)

        //remove horizontal highlight Indicator
        dataSet.setDrawHorizontalHighlightIndicator(false)

        view.data = lineData
        view.animateX(view.context.resources.getInteger(R.integer.feature_charts_duration_chart_animation))


        //When new data is set, hide the MarkerView
        view.highlightValue(null)

        //Marker view
        val markerView = CustomMarkerView(view.context)
        markerView.chartView = view
        view.marker = markerView
        view.isDragEnabled = true
        view.setScaleEnabled(true)
        view.setPinchZoom(true)
        view.invalidate()
    }

    @BindingAdapter("showHide")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    @BindingAdapter("setAnimatedPriceText")
    @JvmStatic
    fun setAnimatedPriceText(textView: TextView, price: String?) {
        price?.let {
            if (textView.text.isEmpty()) {
                textView.text = it
                textView.startAnimation(
                    AnimationUtils.loadAnimation(
                        textView.context,
                        R.anim.feature_charts_fade_in_with_scale
                    )
                )
            }
        }
    }
}

