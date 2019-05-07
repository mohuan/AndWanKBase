package com.weidingqiang.rxfiflibrary3_kotlin.base

/**
 * 作者：weidingqiang on 2018/2/25 15:54
 * 邮箱：dqwei@iflytek.com
 */
/**
 * Created by weidingqiang on 2016/8/2.
 * View基类
 */
interface BaseView {

    fun showErrorMsg(msg: String)

    //=======  State  =======
    fun stateError()

    fun stateEmpty()

    fun stateLoading()

    fun stateMain()
}