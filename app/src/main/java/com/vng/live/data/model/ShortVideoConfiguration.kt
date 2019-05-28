package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */

data class ShortVideoConfiguration(
    @SerializedName("upload_file_max_duration")
    var uploadFileMaxDuration: Int,
    @SerializedName("upload_file_max_size")
    var uploadFileMaxSize: Int,
    @SerializedName("base_url")
    var uploadUrl: String?,
    @Transient
    var uploadFileMinDuration: Int = 3
)
