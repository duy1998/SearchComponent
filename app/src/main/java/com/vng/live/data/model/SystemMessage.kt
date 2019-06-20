package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */

data class SystemMessages(
    @SerializedName("live_screen")
    var liveScreenMessages: List<LiveScreenMessage>?
)

data class LiveScreenMessage(
    @SerializedName("link")
    var link: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("content")
    var content: String?
)
