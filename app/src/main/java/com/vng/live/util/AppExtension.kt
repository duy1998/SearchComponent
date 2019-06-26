package com.vng.live.util

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vng.live.LiveApplication

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 17/06/2019
 */

// region Gson

inline fun <reified T> Gson.fromJson(json: String?): T =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

// endregion

// region Collection

fun <T> List<T>.rangeCheck(index: Int): Boolean =
    index in 0 until size

// endregion

// region Context

fun Context.dp(size: Float): Int =
    kotlin.math.ceil(size * this.resources.displayMetrics.density).toInt()

fun Context.dp2(size: Float): Int =
    kotlin.math.floor(size * this.resources.displayMetrics.density).toInt()

fun Context.dp2px(size: Float): Float =
    size * this.resources.displayMetrics.density

fun Context.sp(size: Float): Float =
    size * this.resources.displayMetrics.scaledDensity

fun Context.getColorCompat(@ColorRes colorRes: Int): Int =
    ContextCompat.getColor(this, colorRes)

// endregion