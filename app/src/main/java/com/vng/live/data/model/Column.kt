package com.vng.live.data.model

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi

data class Column(
    var timeStart: Int = 0,
    private var direction: Float = -1.0f,
    private var maxHeightCol: Float,
    private var minHeightCol: Float,
    private var widthCol: Float,
    private val radiusCol: Float,
    private val paint: Paint,
    private val spacingCol: Float,
    private val step: Float,
    private val countCol: Int
) {
    private var heightCol: Float = minHeightCol
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun draw(canvas: Canvas, timeRun: Int, viewHeight: Int, viewWidth: Int) {
        if (maxHeightCol == -1f) {
            maxHeightCol = viewHeight.toFloat()
        }
        if (widthCol == -1f) {
            widthCol = (viewWidth - (countCol - 1) * spacingCol) / countCol
        }
        if(minHeightCol>maxHeightCol){
            minHeightCol=maxHeightCol
        }
        canvas.drawRoundRect(0f, maxHeightCol - heightCol, widthCol, maxHeightCol, radiusCol, radiusCol, paint)
        if (timeStart < timeRun) {
            heightCol += step * direction
            if (heightCol > maxHeightCol) {
                heightCol = maxHeightCol
                direction = -1f
            } else if (heightCol < minHeightCol) {
                heightCol = minHeightCol
                direction = 1f
            }
        }
    }
}