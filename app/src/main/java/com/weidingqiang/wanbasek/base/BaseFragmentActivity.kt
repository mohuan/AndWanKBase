package com.weidingqiang.wanbasek.base

import com.weidingqiang.rxfiflibrary3_kotlin.base.BasePresenter
import com.weidingqiang.rxfiflibrary3_kotlin.base.BaseView
import com.weidingqiang.rxqweklibrary.base.SimpleFragmentActivity
import com.weidingqiang.wanbasek.app.AppContext
import com.weidingqiang.wanbasek.di.component.ActivityComponent
import com.weidingqiang.wanbasek.di.component.DaggerActivityComponent
import com.weidingqiang.wanbasek.di.module.ActivityModule
import javax.inject.Inject

/**
 * 作者：weidingqiang on 2018/3/28 20:56
 * 邮箱：weidingqiang@163.com
 */
abstract class BaseFragmentActivity<T : BasePresenter<*>> : SimpleFragmentActivity(), BaseView {

    @Inject
    lateinit var mPresenter: T

    protected val activityComponent: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .appComponent(AppContext.instance.appComponent)
                .activityModule(activityModule)
                .build()

    protected val activityModule: ActivityModule
        get() = ActivityModule(this)

    override fun onViewCreated() {
        super.onViewCreated()
        initInject()
        initPresenter()
    }

    override fun onDestroy() {
        mPresenter!!.detachView()

        super.onDestroy()
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