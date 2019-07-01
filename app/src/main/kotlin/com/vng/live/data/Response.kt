package com.vng.live.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Response<T>(
    @SerializedName("error") @Expose val error: String?,

    @SerializedName("msg") @Expose val message: String?,

    @SerializedName("data") @Expose val data: T
)


