package com.catalinjurjiu.wheelbarrowdemo.activitiesdemo

import android.os.Bundle
import android.view.View
import com.catalinjurjiu.wheelbarrowdemo.DaggerApplication
import com.catalinjurjiu.wheelbarrowdemo.R
import com.catalinjurjiu.wheelbarrow.WheelbarrowActivity
import com.catalinjurjiu.wheelbarrowdemo.common.SumViewPresenter
import com.catalinjurjiu.wheelbarrow.common.identityHashCode
import com.catalinjurjiu.wheelbarrowdemo.di.activitiesdemo.Activity2Component
import com.catalinjurjiu.wheelbarrowdemo.di.activitiesdemo.Activity2Module
import kotlinx.android.synthetic.main.binding_info_and_back_button.*
import kotlinx.android.synthetic.main.partial_add_to_sum.*
import kotlinx.android.synthetic.main.partial_current_view_binding_info.*
import javax.inject.Inject

class Activity2 : WheelbarrowActivity<Activity2Component>() {
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

    override fun onCreateInjector(): Activity2Component {
        val module = Activity2Module()
        return (application as DaggerApplication).injectionRoot.getActivity2Component(module)
    }

    private fun initViews() {
        label_screen_name.text = TAG
        label_view_instance.text = this.identityHashCode()
        label_presenter_instance.text = presenter.identityHashCode()
        label_injector_instance.text = cargo.identityHashCode()
        //sum initialization
        initAddToSumButton()
        //go back button
        button_generic_action.text = "Go Back"
        button_generic_action.visibility = View.VISIBLE
        button_generic_action.setOnClickListener {
            finish()
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
        const val TAG = "Activity2"
    }
}