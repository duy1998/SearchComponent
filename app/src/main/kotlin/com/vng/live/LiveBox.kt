package com.vng.live

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class LiveBox@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int=0
): LinearLayout(context,attrs,defStyleAttr) {
    var tv: TextView

    var live: LiveIndicatorView

    init {
        LayoutInflater.from(context).inflate(R.layout.box_live, this)

        tv = this.findViewById(R.id.tv)

        live = this.findViewById(R.id.live)

        this.setBackgroundColor(Color.RED)

        this.setBackgroundResource(R.drawable.shape)
    }
}