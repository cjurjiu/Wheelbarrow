package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.fragment2

import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.Fragment2Component
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.Fragment2Module
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.MainFragmentComponent

class Fragment2Factory(private val parentComponent: MainFragmentComponent) :
        WheelbarrowFragment.Factory<Fragment2Component>() {

    override fun onCreateFragment(): WheelbarrowFragment<Fragment2Component> = Fragment2()

    override fun onRequestCargo(): Fragment2Component {
        val module = Fragment2Module()
        return parentComponent.getFragment2Component(module)
    }
}