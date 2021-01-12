package com.android.cleanarch.bitcoinapp.base

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 *  custom [BitcoinTestRunner]
 */
class BitcoinTestRunner : AndroidJUnitRunner() {
    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestBitcoinApp::class.java.name, context)
    }
}