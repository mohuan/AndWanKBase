package com.weidingqiang.wanbasek.ui.splash.presenter

import com.weidingqiang.wanbasek.base.RxPresenter
import com.weidingqiang.wanbasek.model.DataManager
import com.weidingqiang.wanbasek.ui.splash.contract.SplashContract
import javax.inject.Inject

/**
 * Created by weidingqiang
 */
class SplashPresenter @Inject
constructor(private val mDataManager: DataManager) : RxPresenter<SplashContract.View>(),
    SplashContract.Presenter {

    override val isLogin: Boolean
        get() = mDataManager.loginStatus

    override fun attachView(view: SplashContract.View) {
        super.attachView(view)
        registerEvent()
    }

    private fun registerEvent() {

    }
}