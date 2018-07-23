package com.catalinjurjiu.wheelbarrow.di.activitiesdemo

import com.catalinjurjiu.wheelbarrow.activitiesdemo.Activity2
import com.catalinjurjiu.wheelbarrow.common.SumViewPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Qualifier
import javax.inject.Scope

@Activity2Scope
@Subcomponent(modules = [Activity2Module::class])
abstract class Activity2Component {

    abstract fun inject(activity1: Activity2)
}

@Module
class Activity2Module {

    @Provides
    @Activity2Scope
    fun providePresenter(): SumViewPresenter {
        return SumViewPresenter()
    }
}

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class Activity2Scope
