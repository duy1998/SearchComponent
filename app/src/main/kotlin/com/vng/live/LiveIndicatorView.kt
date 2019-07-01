package com.vng.live

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.vng.live.R.styleable.*
import com.vng.live.Column

class LiveIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int=0
) : View(context, attrs, defStyleAttr){
    private var viewHeight: Int=0

    private var viewWidth: Int=0

    private var maxHeightCol: Float

    private var widthCol: Float=0.0f

    private val radiusCol: Float = 2f

    private val spacingCol: Float

    private val countCol: Int

    private val style:Int

    private val colorCol:Int

    private val FPS:Int

    private var minHeightCol: Float

    private var time = 0

    private var paint: Paint

    private var step :Float

    private val listColumn = ArrayList<Column>()

    init {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.LiveIndicatorView, 0, 0)
        maxHeightCol = attributes.getDimensionPixelSize(LiveIndicatorView_maxColumnHeight, 20).toFloat()
        minHeightCol = attributes.getDimensionPixelSize(LiveIndicatorView_minColumnHeight, 5).toFloat()
        spacingCol = attributes.getDimensionPixelSize(LiveIndicatorView_columnSpacing, 1).toFloat()
        step= attributes.getDimensionPixelSize(LiveIndicatorView_step,20).toFloat()
        countCol = attributes.getInt(LiveIndicatorView_countColumn, 3)
        style = attributes.getInt(LiveIndicatorView_style, 1)
        widthCol=attributes.getDimensionPixelSize(LiveIndicatorView_widthColumn,2).toFloat()
        colorCol=attributes.getColor(LiveIndicatorView_iconColor,Color.WHITE)
        FPS=attributes.getInt(LiveIndicatorView_FPS,30)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = colorCol
        paint.style = Paint.Style.FILL
    }

    private fun registerCol(){
        for (x in 0 until countCol) {
            if (x == 0) {
                listColumn.add(
                    Column(
                        maxHeightCol,
                        minHeightCol,
                        step,
                        radiusCol,
                        0f,
                        maxHeightCol,
                        0f + widthCol,
                        maxHeightCol,
                        x
                    )
                )
            } else {
                listColumn.add(
                    Column(
                        maxHeightCol,
                        minHeightCol,
                        step,
                        radiusCol,
                        listColumn[x - 1].right + spacingCol,
                        maxHeightCol,
                        listColumn[x - 1].right + spacingCol + widthCol,
                        maxHeightCol,
                        x
                    )
                )
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if(style==1) {
            this.layoutParams.height=LinearLayout.LayoutParams.WRAP_CONTENT
            this.layoutParams.width=LinearLayout.LayoutParams.WRAP_CONTENT
            viewWidth = (widthCol * countCol + spacingCol * (countCol - 1)).toInt()
            viewHeight = maxHeightCol.toInt()
        }else{
            when (widthMode) {
                MeasureSpec.EXACTLY -> {
                    viewWidth = widthSize
                }
                MeasureSpec.AT_MOST -> {
                    viewWidth=Math.max(viewWidth,widthSize)
                }
                else -> {
                    viewWidth = 100
                }
            }

            when (heightMode) {
                MeasureSpec.EXACTLY -> {
                    viewHeight = heightSize
                }
                MeasureSpec.AT_MOST -> {
                    viewHeight = heightSize
                }
                else -> {
                    viewHeight = 50
                }
            }
        }
        //custom
        val measuredWidth = MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY)
        val measureHeight = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)

        super.onMeasure(measuredWidth, measureHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if(style==1) {
            this.layoutParams.height=LinearLayout.LayoutParams.WRAP_CONTENT
            this.layoutParams.width=LinearLayout.LayoutParams.WRAP_CONTENT
            viewWidth = width
            viewHeight = height
        }else{
            viewWidth = width
            widthCol =(viewWidth-(countCol-1)*spacingCol)/countCol
            viewHeight = height
            maxHeightCol= viewHeight.toFloat()
        }
        registerCol()
        super.onLayout(changed, left, top, right, bottom)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            for (x in 0 until countCol) {
                listColumn[x].draw(this, paint, time)
            }
            if (time != countCol) {
                time++
            }
            postInvalidateDelayed(1000L / FPS)
        }
    }

}