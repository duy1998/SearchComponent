package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */

data class EntertainmentChannel(
    @SerializedName("streamId")
    var streamId: Int,
    @SerializedName("iconUrl")
    var iconUrl: String?,
    @SerializedName("displayName")
    var displayName: String?
)
