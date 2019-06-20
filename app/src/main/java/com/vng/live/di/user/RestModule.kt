package com.vng.live.di.user

import android.content.Context
import com.vng.live.BuildConfig
import com.vng.live.data.User
import com.vng.live.data.local.AppEnvironment
import com.vng.live.data.remote.DefaultParamsInterceptor
import com.vng.live.data.remote.RetrofitHelper
import com.vng.live.di.*
import com.vng.live.util.Logger
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.http.conn.ssl.StrictHostnameVerifier
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 17/06/2019
 */

@Module
class RestModule(
    private val user: User
) {
    @Provides
    @DefaultInterceptor
    fun provideDefaultParamInterceptor(
        @DeviceId deviceId: String,
        @DeviceName deviceName: String,
        @AppVersion appVersion: String
    ): Interceptor =
        DefaultParamsInterceptor(user.credential.accessToken, deviceId, deviceName, appVersion)

    @Provides
    fun provideHttpCache(@AppContext context: Context): Cache = Cache(context.cacheDir, HTTP_CACHE_SIZE)

    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor {
        if (BuildConfig.DEBUG) httpLogger.i(it.replace("%".toRegex(), "%%"))
    }.apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    fun provideSslContext(): SSLContext? = null

    @Provides
    @UserHttpClient
    fun provideUserHttpClient(
        cache: Cache,
        @DefaultInterceptor interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        sslContext: SSLContext? = null
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            cache(cache)
            addInterceptor(interceptor)
            addInterceptor(loggingInterceptor)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            sslContext?.let {
                sslSocketFactory(sslContext.socketFactory)
                hostnameVerifier(StrictHostnameVerifier())
            }
        }.build()

    @Provides
    @UserScope
    fun provideRestClient(
        env: AppEnvironment,
        @UserHttpClient httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(env.serverUrl)
            client(httpClient)
            RetrofitHelper.callAdapters.forEach { addCallAdapterFactory(it) }
            RetrofitHelper.converter.forEach { addConverterFactory(it) }
        }.build()
    }

    companion object {
        private const val HTTP_CACHE_SIZE: Long = 10 shl 20 // 10MB

        private val httpLogger = Logger.getLogger("RestClient", true)
    }
}
