package com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catalinjurjiu.wheelbarrow.WheelbarrowFragment
import com.catalinjurjiu.wheelbarrow.common.identityHashCode
import com.catalinjurjiu.wheelbarrowdemo.R
import com.catalinjurjiu.wheelbarrowdemo.common.SumViewPresenter
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.MainFragmentComponent
import com.catalinjurjiu.wheelbarrowdemo.di.fragmentsdemo.MainFragmentQualifier
import com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.fragment2.Fragment2
import com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.fragment2.Fragment2Factory
import com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment.childfragment.ChildFragment
import com.catalinjurjiu.wheelbarrowdemo.fragmentsdemo.fragments.mainfragment.childfragment.ChildFragmentFactory
import kotlinx.android.synthetic.main.fragments_demo_main.view.*
import kotlinx.android.synthetic.main.partial_add_to_sum.view.*
import kotlinx.android.synthetic.main.partial_current_view_binding_info.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : WheelbarrowFragment<MainFragmentComponent>() {

    override val name: String
        get() = TAG

    @Inject
    @field:MainFragmentQualifier
    protected lateinit var presenter: SumViewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cargo.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragments_demo_main, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.label_screen_name.text = TAG
        view.label_view_instance.text = this.identityHashCode()
        view.label_presenter_instance.text = presenter.identityHashCode()
        view.label_injector_instance.text = cargo.identityHashCode()
        //sum initialization
        initAddToSumButton(view)
        //add/remove child fragment button
        initAddRemoveChildFragmentButton(view)
        //go to fragment2 with backstack
        view.button_goto_fragment2.setOnClickListener {
            val factory = Fragment2Factory(parentComponent = cargo)
            fragmentManager!!.beginTransaction()
                    .replace(R.id.container_fragments_demo_main,
                            factory.create(),
                            Fragment2.TAG)
                    .addToBackStack(Fragment2.TAG)
                    .commit()
        }
    }

    private fun initAddToSumButton(view: View) {
        view.text_sum_value.text = presenter.sum.toString()
        view.button_sum_add_1.setOnClickListener {
            presenter.addOne()
            view.text_sum_value.text = presenter.sum.toString()
        }
    }

    private fun initAddRemoveChildFragmentButton(view: View) {
        view.button_add_child_fragment.text =
                if (childFragmentManager.findFragmentByTag(ChildFragment.TAG) != null) {
                    getString(R.string.remove_child_frag)
                } else {
                    getString(R.string.add_child_frag)
                }
        //child fragment
        view.button_add_child_fragment.setOnClickListener {
            val frag = childFragmentManager.findFragmentByTag(ChildFragment.TAG)
            if (frag != null) {
                removeChildFragment(fragment = frag)
            } else {
                showChildFragment()
            }
        }
    }

    private fun showChildFragment() {
        view?.button_add_child_fragment?.text = getString(R.string.remove_child_frag)
        val factory = ChildFragmentFactory(parentInjector = cargo)
        childFragmentManager.beginTransaction()
                .add(R.id.container_fragment1_child_fragment, factory.create(), ChildFragment.TAG)
                .commit()
    }

    private fun removeChildFragment(fragment: Fragment) {
        view?.button_add_child_fragment?.text = getString(R.string.add_child_frag)
        childFragmentManager.beginTransaction().remove(fragment).commit()
    }

    companion object {
        const val TAG = "Main Fragment"
    }
}