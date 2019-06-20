package com.vng.live.di.app

import com.google.gson.Gson
import com.vng.live.data.local.AppEnvironment
import com.vng.live.data.local.ApplicationConfigs
import com.vng.live.data.local.UserLocalStorage
import com.vng.live.di.AppVersion
import com.vng.live.di.DeviceId
import com.vng.live.di.DeviceName
import com.vng.live.di.user.RestModule
import com.vng.live.di.user.ServiceModule
import com.vng.live.di.user.UserComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 23/05/2019
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun provideGson(): Gson

    fun provideAppConfig(): ApplicationConfigs

    fun provideAppEnvironment(): AppEnvironment

    fun provideUserLocalStorage(): UserLocalStorage

    @DeviceId
    fun provideDeviceId(): String

    @DeviceName
    fun provideDeviceName(): String

    @AppVersion
    fun provideAppVersion(): String

    fun plusUserComponent(
        restModule: RestModule,
        serviceModule: ServiceModule
    ): UserComponent
}
