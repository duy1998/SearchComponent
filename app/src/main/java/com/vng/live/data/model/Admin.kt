package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */

data class Admin(
    @SerializedName("ban")
    val ban: Ban?
)

data class Ban(
    @SerializedName("rule")
    var rules: List<Rule>?,
    @SerializedName("strategy")
    var strategy: List<Strategy>?
)

data class Rule(
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: Int
)

data class Strategy(
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("duration")
    var duration: Long
)
