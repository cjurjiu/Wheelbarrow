package com.catalinjurjiu.wheelbarrow

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment.Factory
import com.catalinjurjiu.wheelbarrow.common.NamedComponent
import com.catalinjurjiu.wheelbarrow.common.identity
import com.catalinjurjiu.wheelbarrow.log.Chronicle
import com.catalinjurjiu.wheelbarrow.store.ViewModelCargoHolder
import com.catalinjurjiu.wheelbarrow.store.Wheelbarrow

/**
 * Fragment which stores an object as `Cargo` across configuration changes.
 *
 * The `Cargo` is safe to be used immediately after calling `super.onCreate()` in your subclass.
 * After the `Cargo` has been set on the `Fragment`, typically the `Cargo` instance won't be changed.
 *
 * Instances of this Fragment **should only be created** using a subclass of
 * [WheelbarrowFragment.Factory]. Do not use subclasses of this [Fragment] created using the default
 * constructor!
 *
 * Adding to the [FragmentManager][android.support.v4.app.FragmentManager] a [WheelbarrowFragment]
 * which was not initially created using a [WheelbarrowFragment.Factory] will result in a runtime
 * exception.
 *
 * Each subclass of `WheelbarrowFragment` will need to define a [name]. The `TAG` used when adding
 * the `Fragment` to the `FragmentManager` can be also used as the [name] of the `Fragment`.
 *
 * [CargoType] is the type of the `Cargo` to be stored during a configuration change.
 *
 * @see Factory
 *
 * @constructor Creates a new [WheelbarrowFragment].
 *
 * Created by catalinj on 27.02.2018.
 */
@Suppress("UNCHECKED_CAST")
abstract class WheelbarrowFragment<CargoType : Any> : Fragment(), NamedComponent {

    /**
     * The cargo stored by this [WheelbarrowFragment].
     */
    protected open val cargo: CargoType by lazy(LazyThreadSafetyMode.NONE) { cargoInternal }

    private lateinit var cargoInternal: CargoType
    private var doPersistCargoToViewModel: Boolean = false
    private var doReadCargoFromViewModel: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }

    private fun initializeInjector() {
        val cargoStore: ViewModelCargoHolder<CargoType> = ViewModelProviders.of(this)
                .get(name, ViewModelCargoHolder::class.java) as ViewModelCargoHolder<CargoType>
        if (doPersistCargoToViewModel) {
            if (isCargoInitialized()) {
                cargoStore.cargo = cargoInternal
                Chronicle.logDebug(this::class.java.simpleName, "Saved cargo: " +
                        "${cargoStore.identity()} for: ${this.identity()}.")
            } else {
                Chronicle.logError(this::class.java.simpleName, "Cannot store an " +
                        "uninitialised cargo instance.")
                //Cargo not initialised but the flags say that the cargo was initialised and
                //needs to be stored.
                throw IllegalStateException("Cannot store uninitialised Cargo. Please make sure the" +
                        "Fragment is created using a subclass of WheelbarrowFragment.Factory class, " +
                        "not directly via the default constructor.")
            }
        } else if (doReadCargoFromViewModel) {
            if (cargoStore.hasCargo) {
                cargoInternal = cargoStore.cargo
                Chronicle.logDebug(this::class.java.simpleName, "Re-initialised cargo: " +
                        "${cargoStore.identity()} for: ${this.identity()}.")
            } else {
                //Cargo wants to be initialized from the cargo store, but the store has no
                //cargo in it....an error? wrong flags? cargo overwritten by the user?
                Chronicle.logError(this::class.java.simpleName, "Cannot initialize the " +
                        "cargo instance from an empty cargo holder.")
                throw IllegalStateException("Empty cargo holder. Please make sure you didn't override" +
                        "the existing Cargo with an invalid/null value.")
            }
        }
    }

    private fun isCargoInitialized(): Boolean {
        return ::cargoInternal.isInitialized
    }

    /**
     * Factory which performs the initial configuration between a [WheelbarrowFragment] and its
     * `Cargo`.
     *
     * Always instantiate subclasses of [WheelbarrowFragment] using a subclass of this [Factory]. Not
     * doing so will result in a [RuntimeException] being thrown when adding the respective
     * `WheelbarrowFragment` to the [FragmentManager][android.support.v4.app.FragmentManager].
     *
     * This `Factory` works in 2 phases: First it instantiates your `WheelbarrowFragment` subclass
     * in [onCreateFragment]. The fragment returned here is not ready to be used at this point.
     * Then it obtains the required `Cargo` via [onCreateCargo].
     *
     * Finally, when [create] is called, a configured instance of your [WheelbarrowFragment] is
     * returned. The `WheelbarrowFragment` returned by [create()] is ready to be used, and can be
     * added to the `FragmentManager`.
     */
    abstract class Factory<CargoType : Any> : Wheelbarrow<CargoType>,
            com.catalinjurjiu.wheelbarrow.factory.Factory<WheelbarrowFragment<CargoType>> {

        /**
         * Binds the [WheelbarrowFragment] provided via [onCreateFragment] with the `Cargo` provided
         * via [onCreateCargo] and returns a configured `WheelbarrowFragment` ready to be used.
         *
         * @return a safe to use instance of a [WheelbarrowFragment].
         */
        final override fun create(): WheelbarrowFragment<CargoType> {
            val f: WheelbarrowFragment<CargoType> = onCreateFragment()
            val cargo: CargoType = onCreateCargo()
            f.cargoInternal = cargo
            f.doPersistCargoToViewModel = true
            f.doReadCargoFromViewModel = false
            return f
        }

        /**
         * Called when the [Factory] requires the [WheelbarrowFragment] subclass. This is where the
         * to-be-used fragment is created, its initial parameters are set, and where the Fragment is
         * finally returned.
         *
         * The Fragment returned here is **not** ready to be used, until [create] has been called on
         * this [Factory] instance.
         *
         * @return an instance of a subclass of [WheelbarrowFragment], initialized for the user's
         * needs, but not yet ready to be added to the `FragmentManager`.
         *
         * @see Factory
         * @see WheelbarrowFragment
         */
        abstract fun onCreateFragment(): WheelbarrowFragment<CargoType>
    }
}