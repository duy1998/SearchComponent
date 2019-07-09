package com.vng.live.data.remote.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.leonardoxh.livedatacalladapter.Resource
import com.vng.live.data.model.Response
import com.vng.live.data.model.SimpleProfile
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 18/06/2019
 */
interface UserService {
//    @FormUrlEncoded
//    @POST("api_search/search/feature")
//    fun listTopStar(@Field("auth_key") authKey: String): Observable<Response<List<SimpleProfile>>>
//
//    @FormUrlEncoded
//    @POST("api_search/search/by_name")
//    fun search(@Field("auth_key") authKey: String,
//               @Field("q") key: String,
//               @Field("page") pageSize: Int,
//               @Field("size") count: Int): Observable<Response<List<SimpleProfile>>>

    @FormUrlEncoded
    @POST("api_search/search/feature")
    fun listTopStar(@Field("auth_key") authKey: String): LiveData<Resource<Response<List<SimpleProfile>>>>

    @FormUrlEncoded
    @POST("api_search/search/by_name")
    fun search(@Field("auth_key") authKey: String,
               @Field("q") key: String,
               @Field("page") pageSize: Int,
               @Field("size") count: Int): LiveData<Resource<Response<List<SimpleProfile>>>>
}
