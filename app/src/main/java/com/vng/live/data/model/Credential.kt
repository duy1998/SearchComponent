package com.vng.live.data.model

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 14/06/2019
 */

data class Credential(
    val accessToken: String,
    val tokenExpiredTime: Long = Long.MAX_VALUE
)
