package com.vng.live.di.user

import com.vng.live.data.remote.services.*
import com.vng.live.di.UserScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 18/06/2019
 */

@Module
class ServiceModule {
    @Provides
    @UserScope
    fun provideConfigService(retrofit: Retrofit): ConfigService = retrofit.create(ConfigService::class.java)

    @Provides
    @UserScope
    fun provideDownloadService(retrofit: Retrofit): DownloadService = retrofit.create(DownloadService::class.java)

    @Provides
    @UserScope
    fun provideExchangeService(retrofit: Retrofit): ExchangeService = retrofit.create(ExchangeService::class.java)

    @Provides
    @UserScope
    fun provideShortVideoService(retrofit: Retrofit): ShortVideoService = retrofit.create(ShortVideoService::class.java)

    @Provides
    @UserScope
    fun provideStreamService(retrofit: Retrofit): StreamService = retrofit.create(StreamService::class.java)

    @Provides
    @UserScope
    fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)
}
