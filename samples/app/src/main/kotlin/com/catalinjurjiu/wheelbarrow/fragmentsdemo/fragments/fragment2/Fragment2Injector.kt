package com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.fragment2

import com.catalinjurjiu.wheelbarrow.common.SumViewPresenter

interface Fragment2Injector {
    fun inject(fragment: Fragment2)
}

class Fragment2InjectorImpl : Fragment2Injector {

    private val presenter = SumViewPresenter()

    override fun inject(fragment: Fragment2) {
        fragment.presenter = presenter
    }
}