package com.catalinjurjiu.wheelbarrow.fragmentsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.catalinjurjiu.wheelbarrow.DaggerApplication
import com.catalinjurjiu.wheelbarrow.R
import com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment.MainFragment
import com.catalinjurjiu.wheelbarrow.fragmentsdemo.fragments.mainfragment.MainFragmentFactory

class FragmentsDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activities_demo_main)

        if (savedInstanceState == null) {
            val rootComponent = (application as DaggerApplication).injectionRoot
            val factory = MainFragmentFactory(rootComponent = rootComponent)
            val fragment = factory.create()

            supportFragmentManager.beginTransaction()
                    .add(R.id.container_fragments_demo_main, fragment, MainFragment.TAG)
                    .commit()
        }
    }
}