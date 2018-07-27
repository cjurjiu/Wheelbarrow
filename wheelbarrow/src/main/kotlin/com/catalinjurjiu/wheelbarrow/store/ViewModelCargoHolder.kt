package com.catalinjurjiu.wheelbarrow.store

import android.arch.lifecycle.ViewModel

/**
 * Cargo holder which uses the [ViewModel] to store data across configuration changes.
 */
internal class ViewModelCargoHolder<CargoType : Any> : ViewModel(), CargoHolder<CargoType> {

    override lateinit var cargo: CargoType

    override val hasCargo: Boolean
        get() = ::cargo.isInitialized
}