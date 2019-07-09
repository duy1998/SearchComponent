package com.vng.live.data.remote

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory
import com.github.leonardoxh.livedatacalladapter.LiveDataResponseBodyConverterFactory
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 18/06/2019
 */

object RetrofitHelper {
    val callAdapters: List<CallAdapter.Factory> =
            listOf(
                RxJava2CallAdapterFactory.create(),
                LiveDataCallAdapterFactory.create()
            )

    val converter: List<Converter.Factory> =
            listOf(
                LiveDataResponseBodyConverterFactory.create(),
                GsonConverterFactory.create()
            )
}
