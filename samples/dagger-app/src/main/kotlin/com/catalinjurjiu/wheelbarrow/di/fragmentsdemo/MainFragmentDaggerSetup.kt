package com.catalinjurjiu.wheelbarrow.di.fragmentsdemo

import com.catalinjurjiu.wheelbarrow.common.SumViewPresenter
import com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment.MainFragment
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Qualifier
import javax.inject.Scope

@MainFragmentScope
@Subcomponent(modules = [MainFragmentModule::class])
abstract class MainFragmentComponent {

    abstract fun inject(mainFragment: MainFragment)

    abstract fun getFragment2Component(fragment2Module: Fragment2Module): Fragment2Component

    abstract fun getChildFragmentComponent(childFragmentModule: ChildFragmentModule): ChildFragmentComponent
}

@Module
class MainFragmentModule {

    @Provides
    @MainFragmentQualifier
    @MainFragmentScope
    fun providePresenter(): SumViewPresenter {
        return SumViewPresenter()
    }
}

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class MainFragmentScope

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class MainFragmentQualifier
