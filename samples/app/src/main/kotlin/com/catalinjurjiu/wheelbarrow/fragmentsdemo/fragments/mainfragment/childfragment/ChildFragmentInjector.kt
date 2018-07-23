package com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment.childfragment

import com.catalinjurjiu.wheelbarrow.common.SumViewPresenter

interface ChildFragmentInjector {
    fun inject(childFragment: ChildFragment)
}

class ChildFragmentInjectorImpl : ChildFragmentInjector {

    private val presenter: SumViewPresenter = SumViewPresenter()

    override fun inject(childFragment: ChildFragment) {
        childFragment.presenter = this.presenter
    }
}