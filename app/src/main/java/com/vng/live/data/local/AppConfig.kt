package com.vng.live.data.local

import android.content.Context
import android.content.SharedPreferences
import com.vng.live.BuildConfig
import com.vng.live.data.model.ConfigsData

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */

class ApplicationConfigs(context: Context) {
    private val setting: SharedPreferences = context.getSharedPreferences(APP_CONFIG_NAME, Context.MODE_PRIVATE)

    private lateinit var remoteConfig: ConfigsData

    private var currentEnvironment: Int = -1

    @Synchronized fun setRemoteConfig(data: ConfigsData) {
        remoteConfig = data
        setting.edit().putString(PREF_APP_CONFIGS_KEY, remoteConfig.toString()).apply()
    }

    @Synchronized fun getRemoteConfig(): ConfigsData {
        if (!this::remoteConfig.isInitialized) {
            var storedConfig = ConfigsData.from(setting.getString(PREF_APP_CONFIGS_KEY, ""))
            if (storedConfig == null) {
               storedConfig = DEFAULT_CONFIG
            }
            remoteConfig = storedConfig
        }
        return remoteConfig
    }

    @Synchronized fun setCurrentEnvironmentNumber(current: Int) {
        currentEnvironment = current
        setting.edit().putInt(PREF_CURRENT_ENVIRONMENT, currentEnvironment).apply()
    }

    @Synchronized fun getCurrentEnvironmentNumber(): Int {
        if (currentEnvironment == -1) {
            currentEnvironment = setting.getInt(PREF_CURRENT_ENVIRONMENT, BuildConfig.CURRENT_MODE)
        }
        return currentEnvironment
    }

    @Synchronized
    fun getCheckInGiftDetails(): String? {
        //FIXME: return data object instead
        return setting.getString(PREF_CHECK_IN_GIFT_DETAILS, null)
    }

    @Synchronized
    fun saveCheckInGiftDetails(giftDetailsJson: String) {
        //FIXME: pass data object instead
        setting.edit().putString(PREF_CHECK_IN_GIFT_DETAILS, giftDetailsJson).apply()
    }

    companion object {
        val DEFAULT_CONFIG = ConfigsData.default()

        private const val APP_CONFIG_NAME = "app_configs"
        private const val PREF_APP_CONFIGS_KEY = "key_application_configs"
        private const val PREF_CURRENT_ENVIRONMENT = "key_current_environment"
        private const val PREF_CHECK_IN_GIFT_DETAILS = "key_check_in_gift_details"
    }
}
