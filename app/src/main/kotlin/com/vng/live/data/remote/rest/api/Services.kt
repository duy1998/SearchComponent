package com.vng.live.data.remote.rest.api

import com.vng.live.data.Response
import com.vng.live.data.SimpleProfile
import retrofit2.http.*
import rx.Observable

interface Services {
    @FormUrlEncoded
    @POST("api_search/search/feature")
    fun listTopStar(@Field("auth_key") authKey: String): Observable<Response<List<SimpleProfile>>>
    @FormUrlEncoded
    @POST("api_search/search/by_name")
    fun search(@Field("auth_key") authKey: String,
                        @Field("q") key: String,
                        @Field("page") pageSize: Int,
                        @Field("size") count: Int): Observable<Response<List<SimpleProfile>>>
}