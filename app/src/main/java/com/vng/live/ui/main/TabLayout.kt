package com.vng.live.ui.main

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.vng.live.R
import com.vng.live.util.dp
import com.vng.live.util.rangeCheck

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 25/06/2019
 */
class TabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val homeTabIcon = TabButton(context)

    private val videoTabIcon = TabButton(context)

    private val startLiveButton = StartLiveButton(context)

    private val conversationTabIcon = TabButton(context)

    private val profileTabIcon = TabButton(context)

    private val tabs = listOf(homeTabIcon, videoTabIcon, conversationTabIcon, profileTabIcon)

    private var currentSelectedTab = -1

    private var tabSelectListener: OnTabSelectListener? = null

    private var tabReselectListener: OnTabReselectListener? = null

    init {
        clipChildren = false

        homeTabIcon.apply {
            layoutParams = LayoutParams(0, MATCH_PARENT).apply {
                weight = 1f
            }
            icon.setImageResource(R.drawable.ic_tab_animation_home00000)
            setOnClickListener { setSelected(TAB_INDEX_HOME) }
        }
        addView(homeTabIcon)

        videoTabIcon.apply {
            layoutParams = LayoutParams(0, MATCH_PARENT).apply {
                weight = 1f
            }
            icon.setImageResource(R.drawable.ic_tab_animation_short_video00000)
            setOnClickListener { setSelected(TAB_INDEX_VIDEO) }
        }
        addView(videoTabIcon)

        startLiveButton.apply {
            layoutParams = LayoutParams(0, WRAP_CONTENT).apply {
                weight = 1f
            }
            startLiveIcon.setImageResource(R.drawable.ic_start_live_icon)
            startLiveIcon.setBackgroundResource(R.drawable.start_live_icon_background)
        }
        addView(startLiveButton)

        conversationTabIcon.apply {
            layoutParams = LayoutParams(0, MATCH_PARENT).apply {
                weight = 1f
            }
            icon.setImageResource(R.drawable.ic_tab_animation_mess00000)
            setOnClickListener { setSelected(TAB_INDEX_CHAT) }
        }
        addView(conversationTabIcon)

        profileTabIcon.apply {
            layoutParams = LayoutParams(0, MATCH_PARENT).apply {
                weight = 1f
            }
            icon.setImageResource(R.drawable.ic_tab_animation_profile00000)
            setOnClickListener { setSelected(TAB_INDEX_PROFILE) }
        }
        addView(profileTabIcon)

        val selectedIcons = resources.obtainTypedArray(R.array.main_tab_enable_drawables)
        tabs.forEachIndexed { index, tabButton ->
            tabButton.setSelectedDrawable(selectedIcons.getResourceId(index, 0))
        }
        selectedIcons.recycle()

        val unselectedIcons = resources.obtainTypedArray(R.array.main_tab_disable_drawables)
        tabs.forEachIndexed { index, tabButton ->
            tabButton.setUnselectedDrawable(unselectedIcons.getResourceId(index, 0))
        }
        unselectedIcons.recycle()

        setSelected(TAB_INDEX_HOME)
    }

    private fun setSelected(tabIndex: Int) {
        if (!tabs.rangeCheck(tabIndex)) {
            return
        }

        if (tabIndex == currentSelectedTab) {
            tabReselectListener?.onReselected(currentSelectedTab)
        } else {
            if (tabs.rangeCheck(currentSelectedTab)) {
                tabs[currentSelectedTab].deselect()
            }
            currentSelectedTab = tabIndex
            tabs[currentSelectedTab].select()
            tabSelectListener?.onSelected(currentSelectedTab)
        }
    }

    companion object {
        private const val TAB_INDEX_HOME = 0

        private const val TAB_INDEX_VIDEO = 1

        private const val TAB_INDEX_CHAT = 2

        private const val TAB_INDEX_PROFILE = 3
    }
}

class StartLiveButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    val startLiveIcon = ImageView(context)

    init {
        clipChildren = false

        val size = resources.getDimensionPixelSize(R.dimen.main_tab_animated_icon_size)
        startLiveIcon.layoutParams = LayoutParams(size, size).apply {
            gravity = CENTER
            topMargin = context.dp(-4f)
        }
        addView(startLiveIcon)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val drawable = startLiveIcon.drawable
        if (drawable is AnimationDrawable) {
            drawable.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        val drawable = startLiveIcon.drawable
        if (drawable is AnimationDrawable) {
            drawable.stop()
        }
    }
}

class TabButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    val icon: ImageView = ImageView(context)

    var selectedDrawable: Drawable? = null

    var unselectedDrawable: Drawable? = null

    init {
        val size = resources.getDimensionPixelSize(R.dimen.main_tab_icon_size)
        icon.layoutParams = LayoutParams(size, size).apply {
            gravity = CENTER
        }
        addView(icon)
        setBackgroundResource(with(context) {
            val value = TypedValue()
            theme.resolveAttribute(android.R.attr.selectableItemBackground, value, true)
            value.resourceId
        })
    }

    public fun setSelectedDrawable(@DrawableRes resId: Int) {
        selectedDrawable = ContextCompat.getDrawable(context, resId)
    }

    public fun setUnselectedDrawable(@DrawableRes resId: Int) {
        unselectedDrawable = ContextCompat.getDrawable(context, resId)
    }

    public fun select() {
        icon.setImageDrawable(selectedDrawable)
        selectedDrawable?.let {
            if (it is AnimationDrawable) {
                it.start()
            }
        }
    }

    public fun deselect() {
        icon.setImageDrawable(unselectedDrawable)
        unselectedDrawable?.let {
            if (it is AnimationDrawable) {
                it.start()
            }
        }
    }
}

interface OnTabSelectListener {
    fun onSelected(index: Int)
}

interface OnTabReselectListener {
    fun onReselected(index: Int)
}