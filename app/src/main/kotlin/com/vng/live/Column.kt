package com.vng.live

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class Column(
    private val maxHeightCol: Float,
    private val minHeightCol: Float,
    private val step: Float,
    private val radius: Float,
    var left: Float,
    var top: Float,
    var right: Float,
    var bottom: Float,
    private var timeStart: Int
) {
    private var currentHeightCol: Float = 0.0f

    private val mRect = RectF()

    private var mDirection: Float = -1.0f

    init {
        this.mRect.set(left, 0f, right, bottom)
    }

    private fun setHeightCol() {
        currentHeightCol += step * mDirection
        if (currentHeightCol > maxHeightCol) {
            currentHeightCol = maxHeightCol
            mDirection *= -1
        } else if (currentHeightCol < minHeightCol) {
            currentHeightCol = minHeightCol
            mDirection *= -1
        }
        top = maxHeightCol - currentHeightCol
    }

    fun draw(canvas: Canvas, paint: Paint, begin: Int) {
        if (timeStart < begin) {
            setHeightCol()
            mRect.set(left, top, right, bottom)
        }
        canvas.drawRoundRect(mRect, radius, radius, paint)
    }
}