package com.vng.live.di

import javax.inject.Qualifier

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 13/06/2019
 */

@Qualifier
annotation class AppContext

@Qualifier
annotation class DefaultInterceptor

@Qualifier
annotation class DeviceId

@Qualifier
annotation class DeviceName

@Qualifier
annotation class AppVersion

@Qualifier
annotation class UserHttpClient

@Qualifier
annotation class SseHttpClient
