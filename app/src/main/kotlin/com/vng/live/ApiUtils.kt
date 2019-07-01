package com.vng.live

import com.vng.live.data.remote.rest.RetrofitClient
import com.vng.live.data.remote.rest.api.Services

class ApiUtils {
    companion object {
        private const val baseUrl: String = "http://api.360live.vn/"
        fun getServices(): Services {
            return RetrofitClient.getRetrofit(baseUrl)!!.create(Services::class.java)
        }
    }

}