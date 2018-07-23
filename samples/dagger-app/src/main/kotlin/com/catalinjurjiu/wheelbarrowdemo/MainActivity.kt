package com.catalinjurjiu.wheelbarrowdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.catalinjurjiu.wheelbarrowdemo.activitiesdemo.Activity1
import com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.FragmentsDemoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_fragments_demo.setOnClickListener {
            val intent = Intent(this, FragmentsDemoActivity::class.java)
            startActivity(intent)
        }
        button_activities_demo.setOnClickListener {
            val intent = Intent(this, Activity1::class.java)
            startActivity(intent)
        }
    }
}
