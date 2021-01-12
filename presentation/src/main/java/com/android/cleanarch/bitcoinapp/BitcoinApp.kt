package com.android.cleanarch.bitcoinapp

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.android.cleanarch.bitcoinapp.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class BitcoinApp : Application(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}