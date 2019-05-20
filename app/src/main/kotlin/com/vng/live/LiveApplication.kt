package com.vng.live

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.multidex.MultiDex

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 20/05/2019
 */
class LiveApplication: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
        } else {
//            Fabric.with(this)
        }
    }

    companion object {
        lateinit var instance: LiveApplication
    }
}