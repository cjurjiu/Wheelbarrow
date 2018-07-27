package com.catalinjurjiu.wheelbarrow.store

/**
 * Interface that defines a type which holds a reference to a particular object.
 */
@Suppress("AddVarianceModifier")
internal interface CargoHolder<CargoType : Any> {

    /**
     * The cargo to be stored by types implementing the interface.
     */
    val cargo: CargoType?

    /**
     * Indicates whether cargo is available or not.
     */
    val hasCargo: Boolean
}