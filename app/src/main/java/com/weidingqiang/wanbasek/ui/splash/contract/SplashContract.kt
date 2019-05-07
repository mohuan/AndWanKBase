package com.weidingqiang.wanbasek.ui.splash.contract

import com.weidingqiang.rxfiflibrary3_kotlin.base.BasePresenter
import com.weidingqiang.rxfiflibrary3_kotlin.base.BaseView

/**
 * Created by weidingqiang
 */
interface SplashContract {

    interface View : BaseView {
    }

    interface Presenter : BasePresenter<View> {
        val isLogin: Boolean
    }
}