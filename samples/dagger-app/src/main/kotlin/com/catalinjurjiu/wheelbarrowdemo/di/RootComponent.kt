package com.catalinjurjiu.wheelbarrowdemo.di

import com.catalinjurjiu.wheelbarrowdemo.di.activitiesdemo.Activity1Component
import com.catalinjurjiu.wheelbarrowdemo.di.activitiesdemo.Activity1Module
import com.catalinjurjiu.wheelbarrowdemo.di.activitiesdemo.Activity2Component
import com.catalinjurjiu.wheelbarrowdemo.di.activitiesdemo.Activity2Module
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.MainFragmentComponent
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.MainFragmentModule
import dagger.Component

@Component
abstract class RootComponent {

    abstract fun getActivity1Component(module: Activity1Module): Activity1Component

    abstract fun getActivity2Component(module: Activity2Module): Activity2Component

    abstract fun getMainFragmentComponent(module: MainFragmentModule): MainFragmentComponent

}