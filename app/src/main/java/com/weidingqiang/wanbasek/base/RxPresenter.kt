package com.weidingqiang.wanbasek.base

import com.weidingqiang.rxfiflibrary3_kotlin.base.BasePresenter
import com.weidingqiang.rxfiflibrary3_kotlin.base.BaseView
import com.weidingqiang.wanbasek.base.RxBus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * Created by codeest on 2016/8/2.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */
open class RxPresenter<T : BaseView> : BasePresenter<T> {

    var mView: T? = null
    var mCompositeDisposable: CompositeDisposable? = null

    fun unSubscribe() {
        mCompositeDisposable?.dispose()
    }

    fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        } else {
            mCompositeDisposable?.add(disposable)
        }
    }
// 此方法出现问题 暂时禁用
    fun <K> addRxBusSubscribe(eventType: Class<K>, act: Consumer<K>) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        } else {
            mCompositeDisposable?.add(RxBus.toDefaultFlowable(eventType, act))
        }
    }

    override fun detachView() {
        this.mView = null
        unSubscribe()
    }

    override fun attachView(view: T) {
        this.mView = view
        mCompositeDisposable = CompositeDisposable()
    }
}
