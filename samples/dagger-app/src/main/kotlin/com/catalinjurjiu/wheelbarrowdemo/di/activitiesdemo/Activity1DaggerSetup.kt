package com.catalinjurjiu.wheelbarrowdemo.di.activitiesdemo

import com.catalinjurjiu.wheelbarrowdemo.activitiesdemo.Activity1
import com.catalinjurjiu.wheelbarrowdemo.common.SumViewPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

@Activity1Scope
@Subcomponent(modules = [Activity1Module::class])
abstract class Activity1Component {

    abstract fun inject(activity1: Activity1)
}

@Module
class Activity1Module {

    @Provides
    @Activity1Scope
    fun providePresenter(): SumViewPresenter {
        return SumViewPresenter()
    }
}

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class Activity1Scope
