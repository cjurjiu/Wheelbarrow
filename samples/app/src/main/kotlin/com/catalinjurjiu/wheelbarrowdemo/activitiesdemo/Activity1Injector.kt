package com.catalinjurjiu.wheelbarrowdemo.activitiesdemo

import android.util.Log
import com.catalinjurjiu.wheelbarrowdemo.common.SumViewPresenter

interface Activity1Injector {
    fun inject(activity: Activity1)
}

class Activity1InjectorImpl : Activity1Injector {
    val presenter = SumViewPresenter()

    override fun inject(activity: Activity1) {
        Log.d("Cata", "Injecting fragment!")
        activity.presenter = presenter
    }
}