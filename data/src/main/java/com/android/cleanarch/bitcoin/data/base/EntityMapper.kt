package com.android.cleanarch.bitcoin.data.base

/**
 * Interface that provides helper method that facilitates
 * mapping raw/response to entity.
 *
 * @param <M> the remote model input type
 * @param <E> the entity model output type
 */
interface EntityMapper<in M, out E> {

    fun mapFromRemoteRawToEntity(type: M): E

}