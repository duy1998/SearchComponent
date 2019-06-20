package com.vng.live.data


import com.vng.live.data.model.Credential
import com.vng.live.data.model.Profile

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 14/06/2019
 */

const val GUEST_USER_DISPLAY_NAME = "Guest"

data class User(
    val profile: Profile,
    val credential: Credential
)
