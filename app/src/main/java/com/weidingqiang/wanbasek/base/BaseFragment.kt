package com.weidingqiang.wanbasek.base

import android.os.Bundle
import android.view.View
import com.weidingqiang.rxfiflibrary3_kotlin.base.BasePresenter
import com.weidingqiang.rxfiflibrary3_kotlin.base.BaseView
import com.weidingqiang.rxqweklibrary.base.SimpleFragment
import com.weidingqiang.wanbasek.app.AppContext
import com.weidingqiang.wanbasek.di.component.DaggerFragmentComponent
import com.weidingqiang.wanbasek.di.component.FragmentComponent
import com.weidingqiang.wanbasek.di.module.FragmentModule
import javax.inject.Inject

/**
 * 作者：weidingqiang on 2018/3/28 20:53
 * 邮箱：weidingqiang@163.com
 */
abstract class BaseFragment<T : BasePresenter<*>> : SimpleFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    protected val fragmentComponent: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .appComponent(AppContext.instance.appComponent)
                .fragmentModule(fragmentModule)
                .build()

    protected val fragmentModule: FragmentModule
        get() = FragmentModule(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInject()
        initPresenter()
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        if (mPresenter != null) mPresenter!!.detachView()
        super.onDestroyView()
    }

    override fun showErrorMsg(msg: String) {
    }


    override fun stateError() {

    }

    override fun stateEmpty() {

    }

    override fun stateLoading() {

    }


    override fun stateMain() {

    }

    protected abstract fun initInject()

    protected abstract fun initPresenter()
}