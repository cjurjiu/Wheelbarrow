package com.catalinjurjiu.wheelbarrow.store

import android.arch.lifecycle.ViewModel
import com.catalinjurjiu.common.Holder

class CargoStore<T : Any> : ViewModel(), Holder<T> {

    override lateinit var component: T

    override val hasComp: Boolean
        get() = ::component.isInitialized
}