package com.android.cleanarch.bitcoinapp.di.component

import android.app.Application
import com.android.cleanarch.bitcoinapp.BitcoinApp
import com.android.cleanarch.bitcoinapp.di.module.AppModule
import com.android.cleanarch.bitcoinapp.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@PerApplication
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent {

    fun inject(bitcoinApp: BitcoinApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
