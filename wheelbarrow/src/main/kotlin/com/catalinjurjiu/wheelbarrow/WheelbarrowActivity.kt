package com.catalinjurjiu.wheelbarrow

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
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
    protected open val cargo: CargoType by lazy(LazyThreadSafetyMode.NONE) { cargoInternal }

    private lateinit var cargoInternal: CargoType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = getViewModelFactory()
        val cargoHolder: ViewModelCargoHolder<CargoType> = ViewModelProviders.of(this, factory)
                .get(name, ViewModelCargoHolder::class.java) as ViewModelCargoHolder<CargoType>
        if (!cargoHolder.hasCargo) {
            cargoHolder.cargo = onCreateCargo()
        }
        cargoInternal = cargoHolder.cargo

        Chronicle.logDebug(this::class.java.simpleName, "Initialised cargo instance: " +
                "${cargoInternal.identity()} for: ${this.identity()}.")
    }

    final override fun onRetainCustomNonConfigurationInstance(): Any? {
        val current = ViewModelProviders.of(this)
                .get(name, ViewModelCargoHolder::class.java)

        //this workaround with a factory required by https://issuetracker.google.com/issues/73644080
        var factory = getViewModelFactory()
        if (factory == null) {
            factory = WheelbarrowViewModelProvideFactory(currentViewModel = current)
        }
        val clientCustomObject: Any? = onRetainCustomObject()
        return NonConfigDataHolder(factory = factory, clientObject = clientCustomObject)
    }

    final override fun getLastCustomNonConfigurationInstance(): Any? {
        return super.getLastCustomNonConfigurationInstance()
    }

    /**
     * Use this instead of [onRetainNonConfigurationInstance] or [onRetainCustomNonConfigurationInstance].
     *
     * Retrieve later with [getLastCustomNonConfigurationInstance].
     */
    open fun onRetainCustomObject(): Any? {
        return null
    }

    /**
     * Return the value previously returned from [onRetainCustomObject].
     */
    fun getRetainedCustomObject(): Any? {
        return (lastCustomNonConfigurationInstance as NonConfigDataHolder?)?.clientObject
    }

    private fun getViewModelFactory(): ViewModelProvider.Factory? =
            (lastCustomNonConfigurationInstance as NonConfigDataHolder?)?.factory
}

/**
 * Internal class which stores an arbitrary object stored by a client, and a [ViewModelProvider.Factory].
 */
private data class NonConfigDataHolder(val factory: ViewModelProvider.Factory?,
                                       val clientObject: Any?)

private class WheelbarrowViewModelProvideFactory(val currentViewModel: ViewModelCargoHolder<*>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isInstance(currentViewModel)) {
            currentViewModel as T
        } else {
            try {
                modelClass.newInstance()
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }
    }
}