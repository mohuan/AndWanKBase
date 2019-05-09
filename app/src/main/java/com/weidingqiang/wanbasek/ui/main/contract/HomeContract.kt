package com.weidingqiang.wanbasek.ui.main.contract

import com.weidingqiang.rxfiflibrary3_kotlin.base.BasePresenter
import com.weidingqiang.rxfiflibrary3_kotlin.base.BaseView

/**
 * 作者：weidingqiang on 2018/3/29 10:42
 * 邮箱：weidingqiang@163.com
 */
interface HomeContract {

    interface View : BaseView{
        fun responeError(errorMsg: String)
    }


    interface Presenter : BasePresenter<HomeContract.View>

}