package com.catalinjurjiu.wheelbarrowdemo.activitiesdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.catalinjurjiu.wheelbarrowdemo.DaggerApplication
import com.catalinjurjiu.wheelbarrowdemo.R
import com.catalinjurjiu.wheelbarrow.WheelbarrowActivity
import com.catalinjurjiu.wheelbarrowdemo.common.SumViewPresenter
import com.catalinjurjiu.wheelbarrow.common.identityHashCode
import com.catalinjurjiu.wheelbarrowdemo.di.activitiesdemo.Activity1Component
import com.catalinjurjiu.wheelbarrowdemo.di.activitiesdemo.Activity1Module
import kotlinx.android.synthetic.main.binding_info_and_back_button.*
import kotlinx.android.synthetic.main.partial_add_to_sum.*
import kotlinx.android.synthetic.main.partial_current_view_binding_info.*
import javax.inject.Inject

class Activity1 : WheelbarrowActivity<Activity1Component>() {
    override val name: String
        get() = TAG

    @Inject
    protected lateinit var presenter: SumViewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inject this instance
        cargo.inject(this)
        setContentView(R.layout.binding_info_and_back_button)
        //init the views
        initViews()
    }

    override fun onCreateInjector(): Activity1Component {
        val module = Activity1Module()
        return (application as DaggerApplication).injectionRoot.getActivity1Component(module)
    }

    private fun initViews() {
        label_screen_name.text = TAG
        label_view_instance.text = this.identityHashCode()
        label_presenter_instance.text = presenter.identityHashCode()
        label_injector_instance.text = cargo.identityHashCode()
        //sum initialization
        initAddToSumButton()
        //go back button
        button_generic_action.text = "Open Activity 2"
        button_generic_action.visibility = View.VISIBLE
        button_generic_action.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        }
    }

    private fun initAddToSumButton() {
        text_sum_value.text = presenter.sum.toString()
        button_sum_add_1.setOnClickListener {
            presenter.addOne()
            text_sum_value.text = presenter.sum.toString()
        }
    }

    companion object {
        const val TAG = "Activity1"
    }
}