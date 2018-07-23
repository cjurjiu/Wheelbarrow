package com.catalinjurjiu.wheelbarrow.common

fun Any.identityHashCode() = System.identityHashCode(this).toString(16)

fun Any.identity() = "${this::class.java.simpleName}@${this.identityHashCode()}"