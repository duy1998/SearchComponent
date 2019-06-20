package com.vng.live.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 17/06/2019
 */
class DefaultParamsInterceptor(
    private val authKey: String,
    private val deviceId: String,
    private val deviceName: String,
    private val appVersion: String,
    private val platformId : String = 1.toString()
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()

        var request = origin.newBuilder()
            .addHeader("locale", Locale.getDefault().language)
            .addHeader("Authorization", authKey)
            .build()

        val url = request.url().newBuilder()
            .addQueryParameter("devid", deviceId)
            .addQueryParameter("appversion", appVersion)
            .addQueryParameter("device_name", deviceName)
            .addQueryParameter("platform", platformId)
            .build()
        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}
