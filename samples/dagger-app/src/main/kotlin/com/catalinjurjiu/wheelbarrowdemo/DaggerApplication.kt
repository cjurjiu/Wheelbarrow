package com.catalinjurjiu.wheelbarrowdemo

import android.app.Application
import com.catalinjurjiu.wheelbarrow.WheelBarrowConfig
import com.catalinjurjiu.wheelbarrowdemo.di.DaggerRootComponent
import com.catalinjurjiu.wheelbarrowdemo.di.RootComponent

class DaggerApplication : Application() {

    lateinit var injectionRoot: RootComponent
        private set

    override fun onCreate() {
        super.onCreate()
        injectionRoot = DaggerRootComponent.create()
        WheelBarrowConfig.setDebugLogsEnabled(BuildConfig.DEBUG)
    }
}