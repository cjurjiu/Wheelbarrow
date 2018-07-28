package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment.childfragment

import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment

class ChildFragmentFactory(private val injector: ChildFragmentInjector) :
        WheelbarrowFragment.Factory<ChildFragmentInjector>() {

    override fun onCreateFragment(): WheelbarrowFragment<ChildFragmentInjector> = ChildFragment()

    override fun onCreateCargo(): ChildFragmentInjector {
        return injector
    }
}