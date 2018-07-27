package com.catalinjurjiu.wheelbarrow.store

/**
 * Stores a component of type [CargoType] as `Cargo`.
 *
 * [onCreateCargo] will be called when cargo will need to be created/provided for this `Wheelbarrow`
 * instance.
 */
interface Wheelbarrow<CargoType : Any> {

    /**
     * Called when the `Cargo` needs to be provided to the `Wheelbarrow`.
     *
     * Typically, this will be called only once, when the `Cargo` is created/retrieved for the first
     * time.
     */
    fun onCreateCargo(): CargoType
}