package com.vng.live.util

import android.util.Log
import android.util.Log.*
import java.util.*

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 17/06/2019
 */
class Logger {
    private val tag: String

    private val isEnable: Boolean

    constructor(clazz: Class<*>, enable: Boolean) {
//        var tag = clazz.simpleName
//        if (tag.length > 23) {
//            tag = tag.substring(0, 22)
//        }
//        this.tag = tag
        this.tag = clazz.simpleName
        isEnable = enable
    }

    constructor(tag: String, enable: Boolean) {
//        var tag = tag
//        if (tag.length > 23) {
//            tag = tag.substring(0, 22)
//        }
        this.tag = tag
        isEnable = enable
    }

    fun e(fmt: String, vararg args: Any) {
        if (isLoggable(tag, ERROR)) {
            Log.e(tag, format(fmt, *args))
        }
    }

    fun e(t: Throwable, fmt: String, vararg args: Any) {
        if (isLoggable(tag, ERROR)) {
            Log.e(tag, format(fmt, *args), t)
        }
    }

    fun d(fmt: String, vararg args: Any) {
        if (isLoggable(tag, DEBUG)) {
            Log.d(tag, format(fmt, *args))
        }
    }

    fun d(t: Throwable, fmt: String, vararg args: Any) {
        if (isLoggable(tag, DEBUG)) {
            Log.d(tag, format(fmt, *args), t)
        }
    }

    fun i(fmt: String, vararg args: Any) {
        if (isLoggable(tag, INFO)) {
            Log.i(tag, format(fmt, *args))
        }
    }

    fun i(t: Throwable, fmt: String, vararg args: Any) {
        if (isLoggable(tag, INFO)) {
            Log.i(tag, format(fmt, *args), t)
        }
    }

    fun w(fmt: String, vararg args: Any) {
        if (isLoggable(tag, WARN)) {
            Log.w(tag, format(fmt, *args))
        }
    }

    fun w(t: Throwable, fmt: String, vararg args: Any) {
        if (isLoggable(tag, WARN)) {
            Log.w(tag, format(fmt, *args), t)
        }
    }

    fun wtf(fmt: String, vararg args: Any) {
        Log.wtf(tag, format(fmt, *args))
    }

    fun wtf(t: Throwable, fmt: String, vararg args: Any) {
        Log.wtf(tag, format(fmt, *args), t)
    }

    fun v(fmt: String, vararg args: Any) {
        if (isLoggable(tag, VERBOSE)) {
            Log.v(tag, format(fmt, *args))
        }
    }

    fun v(t: Throwable, fmt: String, vararg args: Any) {
        if (isLoggable(tag, VERBOSE)) {
            Log.v(tag, format(fmt, *args), t)
        }
    }

    private fun format(fmt: String, vararg args: Any): String {
        return String.format(Locale.US, fmt, *args)
    }

    companion object {
        fun getLogger(clazz: Class<*>, enabled: Boolean): Logger {
            return Logger(clazz, enabled)
        }

        fun getLogger(tag: String, enable: Boolean): Logger {
            return Logger(tag, enable)
        }
    }
}
