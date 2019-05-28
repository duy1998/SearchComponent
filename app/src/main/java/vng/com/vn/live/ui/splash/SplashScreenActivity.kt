package vng.com.vn.live.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vng.live.LiveApplication
import com.vng.live.R
import com.vng.live.data.model.ConfigsData
import com.vng.live.data.model.Response
import com.vng.live.data.remote.services.ConfigService
import com.vng.live.di.AppVersion
import com.vng.live.di.app.AppComponent
import com.vng.live.di.presentation.PresentationComponent
import com.vng.live.di.user.UserComponent
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 20/05/2019
 */
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var configService: ConfigService

    private lateinit var appComponent: AppComponent

    private lateinit var userComponent: UserComponent

    private lateinit var presentationComponent: PresentationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        appComponent = LiveApplication.instance.injector.appComponent
        userComponent = LiveApplication.instance.injector.userComponent
        presentationComponent = userComponent.plusUiComponent()
        presentationComponent.inject(this)

        configService.getClientConfig(appComponent.provideAppVersion(), appComponent.provideDeviceId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<ConfigsData>> {
                private var disposable: Disposable? = null

                override fun onComplete() {
                    disposable?.dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: Response<ConfigsData>) {
                    Log.d(SplashScreenActivity::class.java.canonicalName, appComponent.provideGson().toJson(t))
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    @Inject
    fun setAppVersion(@AppVersion version: String) {
        appVersion.text = version
    }
}
