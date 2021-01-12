package com.android.cleanarch.bitcoinapp.di.module

import com.android.cleanarch.bitcoinapp.MainActivity
import com.android.cleanarch.bitcoinapp.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindMainActivity(): MainActivity

}
