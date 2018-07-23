package com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment.childfragment

import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment
import com.catalinjurjiu.wheelbarrow.di.fragmentsdemo.ChildFragmentComponent
import com.catalinjurjiu.wheelbarrow.di.fragmentsdemo.ChildFragmentModule
import com.catalinjurjiu.wheelbarrow.di.fragmentsdemo.MainFragmentComponent

class ChildFragmentFactory(private val parentInjector: MainFragmentComponent) :
        WheelbarrowFragment.Factory<ChildFragmentComponent>() {

    override fun onCreateFragment(): WheelbarrowFragment<ChildFragmentComponent> = ChildFragment()

    override fun onRequestCargo(): ChildFragmentComponent {
        val module = ChildFragmentModule()
        return parentInjector.getChildFragmentComponent(module)
    }
}