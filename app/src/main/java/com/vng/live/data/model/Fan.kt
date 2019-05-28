package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 14/06/2019
 */
data class Fan(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("displayName")
    val displayName: String?,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("level")
    val level: Int,
    @SerializedName("point")
    val point: Int,
    @SerializedName("rank")
    val ranking: Int
)
