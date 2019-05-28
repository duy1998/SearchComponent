package com.vng.live.data.remote.services

import com.vng.live.data.model.ConfigsData
import com.vng.live.data.model.Response
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 18/06/2019
 */
interface ConfigService {
    @FormUrlEncoded
    @POST("api_app/get_config")
    fun getClientConfig(
        @Field("appversion") appVersion: String,
        @Field("devid") deviceId: String,
        @Field("platform") platform: String = 1.toString()
    ): Observable<Response<ConfigsData>>
}
