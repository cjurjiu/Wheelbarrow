package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.catalinjurjiu.wheelbarrowdemo.DaggerApplication
import com.catalinjurjiu.wheelbarrowdemo.R
import com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment.MainFragment
import com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment.MainFragmentFactory

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