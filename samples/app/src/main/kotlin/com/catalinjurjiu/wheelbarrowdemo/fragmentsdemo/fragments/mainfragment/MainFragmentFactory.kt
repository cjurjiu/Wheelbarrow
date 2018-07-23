package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment

import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment

class MainFragmentFactory(private val injector: MainFragmentInjector) :
        WheelbarrowFragment.Factory<MainFragmentInjector>() {

    override fun onCreateFragment(): WheelbarrowFragment<MainFragmentInjector> = MainFragment()

    override fun onRequestCargo(): MainFragmentInjector = injector
}