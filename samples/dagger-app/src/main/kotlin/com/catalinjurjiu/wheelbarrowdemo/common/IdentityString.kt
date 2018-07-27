package com.catalinjurjiu.wheelbarrowdemo.common

/**
 * Returns the hex value of [System.identityHashCode] applied to this object as a [String].
 */
internal inline fun Any.identityHashCode() = System.identityHashCode(this).toString(16)

/**
 * Returns the simple class name of this object followed by this object's [identityHashCode].
 */
internal inline fun Any.identity() = "${this::class.java.simpleName}@${this.identityHashCode()}"