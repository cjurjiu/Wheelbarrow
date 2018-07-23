package com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment

import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment
import com.catalinjurjiu.wheelbarrow.di.RootComponent
import com.catalinjurjiu.wheelbarrow.di.fragmentsdemo.MainFragmentComponent
import com.catalinjurjiu.wheelbarrow.di.fragmentsdemo.MainFragmentModule

class MainFragmentFactory(private val rootComponent: RootComponent) :
        WheelbarrowFragment.Factory<MainFragmentComponent>() {

    override fun onCreateFragment(): WheelbarrowFragment<MainFragmentComponent> = MainFragment()

    override fun onRequestCargo(): MainFragmentComponent {
        val module = MainFragmentModule()
        return rootComponent.getMainFragmentComponent(module)
    }
}