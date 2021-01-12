package com.android.cleanarch.bitcoinapp.features.bitcoinchart.custom

import android.content.Context
import android.view.LayoutInflater
import com.android.cleanarch.bitcoinapp.R
import com.android.cleanarch.bitcoinapp.databinding.CustomMarkerViewBinding
import com.android.cleanarch.bitcoinapp.util.CurrencyUtils
import com.android.cleanarch.bitcoinapp.util.TimeUtils
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF


class CustomMarkerView(context: Context) :
    MarkerView(context, R.layout.custom_marker_view) {

    private val markerViewBinding: CustomMarkerViewBinding =
        CustomMarkerViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    override fun refreshContent(e: Entry, highlight: Highlight) {
        markerViewBinding.markerPrice.text = CurrencyUtils.formatAmount(e.y)
        markerViewBinding.markerDate.text = TimeUtils.formatDateStringWithMonths(e.x.toLong())

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }

//    private val totalWidthInPx = resources.displayMetrics.widthPixels

    //    override fun draw(canvas: Canvas, posX: Float, posY: Float) {
//
//        var newPosX = posX
//
//        val width = width
//        if (totalWidthInPx - newPosX - width < width) {
//            newPosX -= width.toFloat()
//        } else if (newPosX < 50f) {
//            newPosX = 0f
//        }
//
//        canvas.translate(newPosX, posY)
//        draw(canvas)
//        canvas.translate(-newPosX, -posY)
//    }
}