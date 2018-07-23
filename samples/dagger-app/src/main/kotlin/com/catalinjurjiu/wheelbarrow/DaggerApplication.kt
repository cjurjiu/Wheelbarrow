package com.catalinjurjiu.wheelbarrow

import android.app.Application
import com.catalinjurjiu.wheelbarrow.di.DaggerRootComponent
import com.catalinjurjiu.wheelbarrow.di.RootComponent

class DaggerApplication : Application() {

    lateinit var injectionRoot: RootComponent
        private set

    override fun onCreate() {
        super.onCreate()
        injectionRoot = DaggerRootComponent.create()
    }
}