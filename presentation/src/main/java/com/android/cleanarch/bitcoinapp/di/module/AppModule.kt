package com.android.cleanarch.bitcoinapp.di.module

import android.app.Application
import android.content.Context
import com.android.cleanarch.bitcoin.data.bitcoinchart.cache.PreferencesHelper
import com.android.cleanarch.bitcoin.data.common.errorhandling.GeneralErrorHandlerImpl
import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorHandler
import com.android.cleanarch.bitcoinapp.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        DatabaseModule::class,
        MainActivityModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class]
)
class AppModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    fun providePreferencesHelper(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }

    @Provides
    @PerApplication
    fun provideErrorHandler(): ErrorHandler {
        return GeneralErrorHandlerImpl()
    }

}