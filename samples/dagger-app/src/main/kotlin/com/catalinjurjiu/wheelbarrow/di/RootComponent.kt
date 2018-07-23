package com.catalinjurjiu.wheelbarrow.di

import com.catalinjurjiu.wheelbarrow.di.activitiesdemo.Activity1Component
import com.catalinjurjiu.wheelbarrow.di.activitiesdemo.Activity1Module
import com.catalinjurjiu.wheelbarrow.di.activitiesdemo.Activity2Component
import com.catalinjurjiu.wheelbarrow.di.activitiesdemo.Activity2Module
import com.catalinjurjiu.wheelbarrow.di.fragmentsdemo.MainFragmentComponent
import com.catalinjurjiu.wheelbarrow.di.fragmentsdemo.MainFragmentModule
import dagger.Component

@Component
abstract class RootComponent {

    abstract fun getActivity1Component(module: Activity1Module): Activity1Component

    abstract fun getActivity2Component(module: Activity2Module): Activity2Component

    abstract fun getMainFragmentComponent(module: MainFragmentModule): MainFragmentComponent

}