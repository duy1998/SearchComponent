package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */
data class NetAddress(
    @SerializedName("port")
    val port: Int,
    @SerializedName("host")
    val host: String?
)
