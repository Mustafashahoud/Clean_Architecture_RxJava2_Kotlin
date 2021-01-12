package com.android.cleanarch.bitcoin.data.base

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 *
 * @param <E> the model type in the data layer
 * @param <D> the model type in the domain layer
 */
interface Mapper<E, D> {

    fun mapFromEntity(type: E): D

    fun mapToEntity(type: D): E

}