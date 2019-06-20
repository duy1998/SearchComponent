package com.vng.live.di.app

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.google.gson.Gson
import com.vng.live.BuildConfig
import com.vng.live.data.local.AppEnvStore
import com.vng.live.data.local.ApplicationConfigs
import com.vng.live.data.local.UserLocalStorage
import com.vng.live.di.AppContext
import com.vng.live.di.AppVersion
import com.vng.live.di.DeviceId
import com.vng.live.di.DeviceName
import com.vng.live.event.EventDispatcher
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 23/05/2019
 */

@Module
class AppModule(context: Context) {

    private val appContext: Context = context.applicationContext

    @Provides
    @AppContext
    fun provideAppContext() = appContext

    @Provides
    @Singleton
    fun provideEventDispatcher() = EventDispatcher.instance

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideAppEnvStore(@AppContext context: Context) = AppEnvStore(context)

    @Provides
    @Singleton
    fun provideAppConfigs(@AppContext context: Context) = ApplicationConfigs(context)

    @Provides
    @Singleton
    fun provideAppEnvironment(appEnvStore: AppEnvStore) =
        appEnvStore.getCurrent()

    @Provides
    @Singleton
    fun provideUserLocalStorage(@AppContext context: Context, gson: Gson) =
            UserLocalStorage(context, gson)

    @Provides
    @DeviceId
    @Singleton
    fun provideDeviceId(): String =
        try {
            Settings.Secure.getString(appContext.getContentResolver(), Settings.Secure.ANDROID_ID)
        } catch (ex: Exception) {
            ""
        }


    @Provides
    @DeviceName
    @Singleton
    fun provideDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            model.capitalize()
        } else "${manufacturer.capitalize()} $model"
    }

    @Provides
    @AppVersion
    @Singleton
    fun provideAppVersion(): String = BuildConfig.VERSION_NAME
}
