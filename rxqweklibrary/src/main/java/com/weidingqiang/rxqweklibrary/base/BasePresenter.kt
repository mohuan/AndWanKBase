package com.weidingqiang.rxfiflibrary3_kotlin.base

/**
 * Created by weidingqiang on 2016/8/2.
 * Presenter基类
 */
interface BasePresenter<in T : BaseView> {

    fun attachView(view: T)

    fun detachView()
}
