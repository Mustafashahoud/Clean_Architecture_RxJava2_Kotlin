package com.android.cleanarch.bitcoinapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.android.cleanarch.bitcoinapp.util.AppViewModelFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}