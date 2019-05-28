package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */
data class FirstPrizeInfo(
    @SerializedName("diamond")
    var diamond: Int,
    @SerializedName("candy")
    var candy: Int,
    @SerializedName("exp")
    var exp: Int,
    @SerializedName("purchased")
    var purchased: Boolean,
    @SerializedName("iapItemId")
    var iapItemId: String?,
    @SerializedName("usd")
    var usd: Float,
    @SerializedName("vnd")
    var vnd: Int,
    @Transient
    var currencyCode: String?,
    @Transient
    var price: String?,
    @Transient
    var priceAmountMicros: Long
    )
