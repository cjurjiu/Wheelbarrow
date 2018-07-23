package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment

import com.catalinjurjiu.wheelbarrowdemo.common.SumViewPresenter

interface MainFragmentInjector {
    fun inject(fragment: MainFragment)
}

class MainFragmentInjectorImpl : MainFragmentInjector {

    private val presenter = SumViewPresenter()

    override fun inject(fragment: MainFragment) {
        fragment.presenter = this.presenter
    }
}