package com.vng.live.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class SimpleProfile (
    @SerializedName("userId") @Expose val userId: Int,

    @SerializedName("userName") @Expose val userName: String?=null,

    @SerializedName("displayName") @Expose val displayName: String?=null,

    @SerializedName("avatar") @Expose val avatar: String?=null,

    @SerializedName("isFollow") @Expose val isFollow: Int,

    @SerializedName("level") @Expose val level: Int,

    @SerializedName("isLive") @Expose val isLive: Boolean,

    @SerializedName("streamType") @Expose val streamType: Int,

    @SerializedName("verified") @Expose val verified: Boolean,

    @SerializedName("badges") @Expose val badges: List<Any>?
)