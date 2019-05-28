package com.vng.live.di

import android.content.Context
import com.vng.live.data.User
import com.vng.live.di.user.ServiceModule
import com.vng.live.di.app.AppComponent
import com.vng.live.di.app.AppModule
import com.vng.live.di.app.DaggerAppComponent
import com.vng.live.di.user.RestModule
import com.vng.live.di.user.UserComponent

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 20/06/2019
 */

class Injector(private val appContext: Context) {
    lateinit var appComponent: AppComponent
        private set

    lateinit var userComponent: UserComponent
        private set

    fun createAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(appContext))
            .build()
    }

    fun createUserComponent(user: User) {
        userComponent = appComponent.plusUserComponent(
            RestModule(user),
            ServiceModule()
        )
    }
}