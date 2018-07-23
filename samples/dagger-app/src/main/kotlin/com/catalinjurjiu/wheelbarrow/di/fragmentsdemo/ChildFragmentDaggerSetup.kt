package com.catalinjurjiu.wheelbarrow.di.fragmentsdemo

import com.catalinjurjiu.wheelbarrow.common.SumViewPresenter
import com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment.childfragment.ChildFragment
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Qualifier
import javax.inject.Scope

@ChildFragmentScope
@Subcomponent(modules = [ChildFragmentModule::class])
abstract class ChildFragmentComponent {
    abstract fun inject(mainFragment: ChildFragment)
}

@Module
class ChildFragmentModule {

    @Provides
    @ChildFragmentQualifier
    @ChildFragmentScope
    fun providePresenter(): SumViewPresenter {
        return SumViewPresenter()
    }
}

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ChildFragmentQualifier

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ChildFragmentScope