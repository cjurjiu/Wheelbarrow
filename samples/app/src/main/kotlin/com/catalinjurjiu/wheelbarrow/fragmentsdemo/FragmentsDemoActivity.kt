package com.catalinjurjiu.wheelbarrow.fragmentsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.catalinjurjiu.wheelbarrow.R
import com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment.MainFragment
import com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment.MainFragmentFactory
import com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment.MainFragmentInjectorImpl

class FragmentsDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activities_demo_main)

        if (savedInstanceState == null) {

            val factory = MainFragmentFactory(injector = MainFragmentInjectorImpl())
            val fragment = factory.create()

            supportFragmentManager.beginTransaction()
                    .add(R.id.container_fragments_demo_main, fragment, MainFragment.TAG)
                    .commit()
        }
    }
}