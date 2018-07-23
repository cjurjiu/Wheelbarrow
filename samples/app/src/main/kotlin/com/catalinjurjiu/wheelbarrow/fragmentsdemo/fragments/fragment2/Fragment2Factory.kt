package com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.fragment2

import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment

class Fragment2Factory(private val injector: Fragment2Injector) :
        WheelbarrowFragment.Factory<Fragment2Injector>() {

    override fun onCreateFragment(): WheelbarrowFragment<Fragment2Injector> = Fragment2()

    override fun onRequestCargo(): Fragment2Injector = injector
}