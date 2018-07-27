package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment
import com.catalinjurjiu.wheelbarrowdemo.R
import com.catalinjurjiu.wheelbarrowdemo.common.SumViewPresenter
import com.catalinjurjiu.wheelbarrowdemo.common.identityHashCode
import kotlinx.android.synthetic.main.binding_info_and_back_button.view.*
import kotlinx.android.synthetic.main.partial_add_to_sum.view.*
import kotlinx.android.synthetic.main.partial_current_view_binding_info.view.*

/*
 * A simple [Fragment] subclass.
 *
 */
class Fragment2 : WheelbarrowFragment<Fragment2Injector>() {

    override val name: String
        get() = TAG

    internal lateinit var presenter: SumViewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cargo.inject(fragment = this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.binding_info_and_back_button, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.label_screen_name.text = TAG
        view.label_view_instance.text = this.identityHashCode()
        view.label_presenter_instance.text = presenter.identityHashCode()
        view.label_injector_instance.text = cargo.identityHashCode()
        //sum initialization
        initAddToSumButton(view)
        //go back button
        view.button_generic_action.text = "Go back"
        view.button_generic_action.visibility = View.VISIBLE
        view.button_generic_action.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun initAddToSumButton(view: View) {
        view.text_sum_value.text = presenter.sum.toString()
        view.button_sum_add_1.setOnClickListener {
            presenter.addOne()
            view.text_sum_value.text = presenter.sum.toString()
        }
    }

    companion object {
        const val TAG = "Fragment2"
    }
}