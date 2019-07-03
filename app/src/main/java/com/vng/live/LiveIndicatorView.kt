package com.vng.live

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.vng.live.R.styleable.*
import com.vng.live.data.model.Column

class LiveIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewHeight: Int = 0

    private var viewWidth: Int = 0

    private var maxColHeight: Float

    private var colWidth: Float = 0.0f

    private var colRadius: Float = 2f
        set(value) {
            if(value>0) field =value
        }

    private val colSpacing: Float

    private val colCount: Int

    private val style: Int

    private var colColor: Int = 0

    private var fps: Int = 0
        set(value) {
            if(value>0){
                field =value
            }
        }

    private var minColHeight: Float

    private var paint: Paint

    private var step: Float

    private val listColumn = ArrayList<Column>()

    private val listSavingCanvas= ArrayList<Int>()

    private var timeRun: Int = 0

    private fun saveCanvas(canvas: Canvas){
        listSavingCanvas.add(canvas.save())
    }

    fun setTimeStart(intArray: IntArray){
        for(x in 0 until colCount){
            listColumn[x].timeStart=intArray[x]
        }
        timeRun=0
    }

    init {
        val attributes = context.theme.obtainStyledAttributes(attrs, LiveIndicatorView, 0, 0)
        maxColHeight = attributes.getDimensionPixelSize(LiveIndicatorView_maxColumnHeight, -1).toFloat()
        minColHeight = attributes.getDimensionPixelSize(LiveIndicatorView_minColumnHeight, 5).toFloat()
        colSpacing = attributes.getDimensionPixelSize(LiveIndicatorView_columnSpacing, 1).toFloat()
        step = attributes.getDimensionPixelSize(LiveIndicatorView_step, 20).toFloat()
        colCount = attributes.getInt(LiveIndicatorView_countColumn, 3)
        style = attributes.getInt(LiveIndicatorView_style, 1)
        if (style == 1 && maxColHeight == -1f) {
            maxColHeight = 20f
        }
        colWidth = attributes.getDimensionPixelSize(LiveIndicatorView_widthColumn, -1).toFloat()
        if (style == 1 && colWidth == -1f) {
            colWidth = 2f
        }
        if (style == 0) {
            maxColHeight = -1f
            colWidth = -1f
        }
        colColor = attributes.getColor(LiveIndicatorView_iconColor, Color.WHITE)
        colRadius = attributes.getDimensionPixelSize(LiveIndicatorView_columnRadius, 0).toFloat()
        fps = attributes.getInt(LiveIndicatorView_FPS, 30)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = colColor
        paint.style = Paint.Style.FILL
        for (x in 0 until colCount) {
            this.listColumn.add(
                Column(
                    x, 1.0f, maxColHeight, minColHeight,
                    colWidth,
                    colRadius,
                    paint,
                    colSpacing,
                    step,
                    colCount
                )
            )
        }
        setTimeStart(intArrayOf(1,1,4))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (style == 1) {
            viewWidth = (colWidth * colCount + colSpacing * (colCount - 1)).toInt()
            viewHeight = maxColHeight.toInt()
        } else {
            viewWidth = when (widthMode) {
                MeasureSpec.EXACTLY -> {
                    widthSize
                }
                MeasureSpec.AT_MOST -> {
                    widthSize
                }
                else -> {
                    100
                }
            }
            viewHeight = when (heightMode) {
                MeasureSpec.EXACTLY -> {
                    heightSize
                }
                MeasureSpec.AT_MOST -> {
                    heightSize
                }
                else -> {
                    50
                }
            }
        }
        val measuredWidth = MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY)
        val measureHeight = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)
        super.onMeasure(measuredWidth, measureHeight)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            for (x in 0 until colCount) {
                listColumn[x].draw(this, timeRun, height, width)
                saveCanvas(this)
                translate((width - (colCount - 1) * colSpacing) / colCount + colSpacing, 0f)
            }
            restoreToCount(listSavingCanvas[0])
            timeRun++
            postInvalidateDelayed(1000L / fps)
        }
    }

}