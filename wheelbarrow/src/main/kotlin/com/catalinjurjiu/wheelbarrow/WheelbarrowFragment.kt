package com.catalinjurjiu.wheelbarrow

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.catalinjurjiu.common.NamedComponent
import com.catalinjurjiu.wheelbarrow.common.identity
import com.catalinjurjiu.wheelbarrow.log.Chronicle
import com.catalinjurjiu.wheelbarrow.store.CargoStore

/**
 * T is the type of the Injector to be stored during a configuration change.
 *
 * Name is used to identify the fragment in the ViewModelProvider's store. The Fragment TAG should be
 * enough.
 *
 * Created by catalinj on 27.02.2018.
 */
@Suppress("UNCHECKED_CAST")
abstract class WheelbarrowFragment<CargoType : Any> : Fragment(), NamedComponent {

    private var doPersistCargoToViewModel: Boolean = false
    private var doReadCargoFromViewModel: Boolean = true
    protected lateinit var cargo: CargoType
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }

    private fun initializeInjector() {
        val cargoStore = ViewModelProviders.of(this)
                .get(name, CargoStore::class.java) as CargoStore<CargoType>
        if (doPersistCargoToViewModel) {
            if (isCargoInitialized()) {
                cargoStore.component = cargo
                Chronicle.logDebug(this::class.java.simpleName, "Saved cargo: " +
                        "${cargoStore.identity()} for: ${this.identity()}.")
            } else {
                //Injector not initialised but the flags say that the injector was initialised and
                //needs to be stored. Decide how to handle this...maybe just throw an Exception?
                //currently perform a silent failure
                Chronicle.logError(this::class.java.simpleName, "Cannot store an " +
                        "uninitialised cargo instance.")
            }
        } else if (doReadCargoFromViewModel) {
            if (cargoStore.hasComp) {
                cargo = cargoStore.component
                Chronicle.logDebug(this::class.java.simpleName, "Re-initialised cargo: " +
                        "${cargoStore.identity()} for: ${this.identity()}.")
            } else {
                //Injector wants to be initialized from the injector store, but the store has no
                //injector in it....an error? check flags!
                //currently fail silently
                Chronicle.logError(this::class.java.simpleName, "Cannot initialize the " +
                        "cargo instance from an empty cargo store.")
            }
        }
    }

    private fun isCargoInitialized(): Boolean {
        return ::cargo.isInitialized
    }

    abstract class Factory<CargoType : Any> : com.catalinjurjiu.common.Factory<WheelbarrowFragment<CargoType>> {

        final override fun create(): WheelbarrowFragment<CargoType> {
            val f: WheelbarrowFragment<CargoType> = onCreateFragment()
            val daggerComponent: CargoType = onRequestCargo()
            f.cargo = daggerComponent
            f.doPersistCargoToViewModel = true
            f.doReadCargoFromViewModel = false
            return f
        }

        abstract fun onCreateFragment(): WheelbarrowFragment<CargoType>

        abstract fun onRequestCargo(): CargoType
    }
}