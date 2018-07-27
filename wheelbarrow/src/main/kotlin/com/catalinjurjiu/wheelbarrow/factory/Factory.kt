package com.catalinjurjiu.wheelbarrow.factory

/**
 * Represents a simple factory interface for type [T].
 */
internal interface Factory<out T> {

    /**
     * Creates an instance of [T].
     */
    fun create(): T
}