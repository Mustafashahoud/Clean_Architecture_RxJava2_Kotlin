package com.android.cleanarch.bitcoin.domain.base

import com.petertackage.kotlinoptions.Option
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


/**
 * Abstract class for a UseCase that returns an instance of a [Single].
 */
abstract class SingleUseCase<T, in Params : Any> {

    /**
     * Builds a [Single] which will be used when the current [SingleUseCase] is executed.
     */
    abstract fun buildUseCase(params: Option<Params>): Single<T>

    /**
     * Executes the current use case.
     */
    fun execute(params: Option<Params>): Single<T> {
        return buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
    }
}