package com.vng.live.di.user

import com.vng.live.di.UserScope
import com.vng.live.di.presentation.PresentationComponent
import dagger.Subcomponent
import retrofit2.Retrofit

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 17/06/2019
 */

@Subcomponent(modules = [RestModule::class])
@UserScope
interface UserComponent {
    fun plusUiComponent(): PresentationComponent

    fun provideRestClient(): Retrofit
}
