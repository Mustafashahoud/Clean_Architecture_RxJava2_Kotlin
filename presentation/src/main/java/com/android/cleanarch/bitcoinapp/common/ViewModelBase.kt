package com.android.cleanarch.bitcoinapp.common

import androidx.lifecycle.ViewModel
import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorEntity
import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorHandler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class ViewModelBase(
    private val errorHandler: ErrorHandler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected fun getErrorType(throwable: Throwable?): ErrorEntity {
        return if (throwable != null) {
            errorHandler.getError(throwable)
        } else ErrorEntity.Unknown
    }

    protected fun addToDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}