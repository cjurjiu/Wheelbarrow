package com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo

import com.catalinjurjiu.wheelbarrowdemo.common.SumViewPresenter
import com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.fragment2.Fragment2
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Qualifier
import javax.inject.Scope

@Fragment2Scope
@Subcomponent(modules = [Fragment2Module::class])
abstract class Fragment2Component {
    abstract fun inject(mainFragment: Fragment2)
}

@Module
class Fragment2Module {

    @Provides
    @Fragment2Scope
    @Fragment2Qualifier
    fun providePresenter(): SumViewPresenter {
        return SumViewPresenter()
    }
}

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class Fragment2Qualifier

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class Fragment2Scope