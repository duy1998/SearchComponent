package com.vng.live.data.local

import android.content.Context
import com.vng.live.BuildConfig

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 23/05/2019
 */
class AppEnvironment(
    val mode: Int,
    val serverUrl: String,
    val shortVideoUrl: String,
    val sseUrl: String
) {

    fun isProduction() = MODE_PRODUCTION == mode

    fun isDevelopment() = MODE_DEVELOPMENT == mode

    companion object {
        const val MODE_PRODUCTION = BuildConfig.MODE_PRODUCTION
        const val MODE_DEVELOPMENT = BuildConfig.MODE_DEVELOPMENT

        val DEVELOPMENT = AppEnvironment(
            MODE_DEVELOPMENT,
            BuildConfig.DEVELOPMENT_SERVER,
            BuildConfig.SHORT_VIDEO_DEVELOPMENT_SERVER,
            BuildConfig.SSE_DEVELOPMENT_SERVER
        )

        val PRODUCTION = AppEnvironment(
            MODE_PRODUCTION,
            BuildConfig.PRODUCTION_SERVER,
            BuildConfig.SHORT_VIDEO_PRODUCTION_SERVER,
            BuildConfig.SSE_PRODUCTION_SERVER_SERVER
        )
    }
}

class AppEnvStore(context: Context) {
    private val pref = context.getSharedPreferences(APP_ENV_STORE_NAME, Context.MODE_PRIVATE)

    private var currentModeNumber = pref.getInt(PREF_CURRENT_ENVIRONMENT, BuildConfig.CURRENT_MODE)

    private val availableMode = mapOf(
        AppEnvironment.MODE_PRODUCTION to AppEnvironment.PRODUCTION,
        AppEnvironment.MODE_DEVELOPMENT to AppEnvironment.DEVELOPMENT
    )

    fun getCurrent(): AppEnvironment {
        return availableMode[currentModeNumber]?: AppEnvironment.PRODUCTION
    }

    fun switch() {
        currentModeNumber = (currentModeNumber + 1) and 1
        pref.edit().putInt(PREF_CURRENT_ENVIRONMENT, currentModeNumber).apply()
    }

    companion object {
        private const val APP_ENV_STORE_NAME = "app_env"
        private const val PREF_CURRENT_ENVIRONMENT = "key_current_environment"
    }
}
