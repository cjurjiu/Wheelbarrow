package com.catalinjurjiu.common

/**
 * Represents a simple factory interface.
 */
interface Factory<out T> {
    fun create(): T
}