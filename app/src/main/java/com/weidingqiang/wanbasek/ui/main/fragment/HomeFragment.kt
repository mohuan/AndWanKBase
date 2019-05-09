package com.weidingqiang.wanbasek.ui.main.fragment

import android.os.Bundle
import com.weidingqiang.wanbasek.R
import com.weidingqiang.wanbasek.base.RootFragment
import com.weidingqiang.wanbasek.ui.main.contract.HomeContract
import com.weidingqiang.wanbasek.ui.main.presenter.HomePresenter

/**
 * 首页作业
 */
class HomeFragment : RootFragment<HomePresenter>(), HomeContract.View {


    val TAG = HomeFragment::class.java.simpleName

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initInject() = fragmentComponent.inject(this)

    override fun initPresenter() = mPresenter.attachView(this)

    override fun initEventAndData() {

    }

    override fun responeError(errorMsg: String) {
    }

    override fun onSupportVisible() {
        super.onSupportVisible()

    }

    companion object {

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}