package com.catalinjurjiu.wheelbarrow

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.catalinjurjiu.common.NamedComponent
import com.catalinjurjiu.wheelbarrow.common.identity
import com.catalinjurjiu.wheelbarrow.log.Chronicle
import com.catalinjurjiu.wheelbarrow.store.CargoStore

/**
 * [CargoType] is the type of data to be stored during a configuration change.
 */
@Suppress("UNCHECKED_CAST")
abstract class WheelbarrowActivity<CargoType : Any> : AppCompatActivity(), NamedComponent {

    lateinit var cargo: CargoType
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this)
                .get(name, CargoStore::class.java) as CargoStore<CargoType>
        if (!viewModel.hasComp) {
            viewModel.component = onCreateInjector()
        }
        cargo = viewModel.component

        Chronicle.logDebug(this::class.java.simpleName, "Initialised cargo instance: " +
                "${cargo.identity()} for: ${this.identity()}.")
    }

    /**
     * Called when the Cargo instance needs to be created.
     */
    abstract fun onCreateInjector(): CargoType
}