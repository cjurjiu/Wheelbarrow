package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment.childfragment

import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.ChildFragmentComponent
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.ChildFragmentModule
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.MainFragmentComponent

class ChildFragmentFactory(private val parentInjector: MainFragmentComponent) :
        WheelbarrowFragment.Factory<ChildFragmentComponent>() {

    override fun onCreateFragment(): WheelbarrowFragment<ChildFragmentComponent> = ChildFragment()

    override fun onCreateCargo(): ChildFragmentComponent {
        val module = ChildFragmentModule()
        return parentInjector.getChildFragmentComponent(module)
    }
}