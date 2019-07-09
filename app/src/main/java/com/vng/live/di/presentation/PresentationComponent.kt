package com.vng.live.di.presentation

import com.vng.live.di.UiScope
import com.vng.live.ui.main.search.SearchRepository
import com.vng.live.ui.main.search.SearchViewModel
import dagger.Subcomponent
import vng.com.vn.live.ui.splash.SplashScreenActivity

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 18/06/2019
 */

@UiScope
@Subcomponent(modules = [])
interface PresentationComponent {
    fun inject(activity: SplashScreenActivity)
    fun inject(repository: SearchRepository)
}
