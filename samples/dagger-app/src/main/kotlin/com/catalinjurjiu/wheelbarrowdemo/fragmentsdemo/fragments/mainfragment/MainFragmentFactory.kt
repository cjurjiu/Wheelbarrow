package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment

import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment
import com.catalinjurjiu.wheelbarrowdemo.di.RootComponent
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.MainFragmentComponent
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.MainFragmentModule

class MainFragmentFactory(private val rootComponent: RootComponent) :
        WheelbarrowFragment.Factory<MainFragmentComponent>() {

    override fun onCreateFragment(): WheelbarrowFragment<MainFragmentComponent> = MainFragment()

    override fun onCreateCargo(): MainFragmentComponent {
        val module = MainFragmentModule()
        return rootComponent.getMainFragmentComponent(module)
    }
}