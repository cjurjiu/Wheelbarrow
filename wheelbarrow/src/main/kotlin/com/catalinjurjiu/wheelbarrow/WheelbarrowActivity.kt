package com.catalinjurjiu.wheelbarrow

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.catalinjurjiu.wheelbarrow.common.NamedComponent
import com.catalinjurjiu.wheelbarrow.common.identity
import com.catalinjurjiu.wheelbarrow.log.Chronicle
import com.catalinjurjiu.wheelbarrow.store.ViewModelCargoHolder
import com.catalinjurjiu.wheelbarrow.store.Wheelbarrow

/**
 * AppCompatActivity which stores an object as `Cargo` across configuration changes.
 *
 * The `Cargo` is safe to be used immediately after calling `super.onCreate(...)` in your subclass.
 *
 * The first time the [AppCompatActivity] is created, the `Cargo` will be empty. Because of this,
 * [onCreateCargo] will be called and a valid `Cargo` instance will need to be returned.
 *
 * Once the `Cargo` has been set on the `Activity`, the `Cargo` instance won't be changed, and
 * [onCreateCargo] will not be called again when the `Activity` is recreated due to a config change.
 *
 * Each subclass of `WheelbarrowActivity` will need to define a [name]. Usually, the simple class
 * name of the `Activity` can also used as the [name].
 *
 * [CargoType] is the type of the `Cargo` to be stored during a configuration change.
 *
 * Created by catalinj on 27.02.2018.
 */
@Suppress("UNCHECKED_CAST")
abstract class WheelbarrowActivity<CargoType : Any> : Wheelbarrow<CargoType>, AppCompatActivity(),
        NamedComponent {

    /**
     * The cargo stored by this [WheelbarrowActivity].
     */
    protected lateinit var cargo: CargoType
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cargoHolder: ViewModelCargoHolder<CargoType> = ViewModelProviders.of(this)
                .get(name, ViewModelCargoHolder::class.java) as ViewModelCargoHolder<CargoType>
        if (!cargoHolder.hasCargo) {
            cargoHolder.cargo = onCreateCargo()
        }
        cargo = cargoHolder.cargo

        Chronicle.logDebug(this::class.java.simpleName, "Initialised cargo instance: " +
                "${cargo.identity()} for: ${this.identity()}.")
    }
}