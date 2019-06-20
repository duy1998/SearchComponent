package com.vng.live.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 17/06/2019
 */

inline fun <reified T> Gson.fromJson(json: String?): T =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)
