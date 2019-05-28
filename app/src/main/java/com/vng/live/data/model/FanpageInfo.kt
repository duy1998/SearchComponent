package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */

data class Fanpage(
    @SerializedName("facebook")
    val fanpageInfo: FanpageInfo?
)

data class FanpageInfo(
    @SerializedName("pageUrl")
    var pageUrl: String?,
    @SerializedName("pageId")
    var pageId: String?
)
