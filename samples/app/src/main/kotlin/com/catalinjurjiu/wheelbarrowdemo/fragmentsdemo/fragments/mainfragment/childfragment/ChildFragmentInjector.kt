package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment.childfragment

import com.catalinjurjiu.wheelbarrowdemo.common.SumViewPresenter

interface ChildFragmentInjector {
    fun inject(childFragment: ChildFragment)
}

class ChildFragmentInjectorImpl : ChildFragmentInjector {

    private val presenter: SumViewPresenter = SumViewPresenter()

    override fun inject(childFragment: ChildFragment) {
        childFragment.presenter = this.presenter
    }
}