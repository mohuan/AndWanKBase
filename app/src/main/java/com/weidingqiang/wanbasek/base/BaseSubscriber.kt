package com.weidingqiang.wanbasek.base

import com.blankj.utilcode.util.LogUtils
import com.weidingqiang.rxfiflibrary3_kotlin.base.BaseView
import com.weidingqiang.wanbasek.model.http.exception.ApiException
import io.reactivex.subscribers.ResourceSubscriber
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * 作者：weidingqiang on 2018/4/10 13:21
 * 邮箱：weidingqiang@163.com
 */

abstract class BaseSubscriber<T>(private val view: BaseView?) : ResourceSubscriber<T>() {

    private var msg: String? = null

    abstract fun onSuccess(t: T)

    constructor(view: BaseView?, msg: String?) : this(view) {
        this.msg = msg
    }

    open fun onFailure(code: Int, message: String) {

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onComplete() {

    }

    override fun onNext(response: T) {
        view?.let {
//            it.complete()
            onSuccess(response)
        } ?: return@onNext
    }


    override fun onError(e: Throwable) {
        view?.let {
            if (!msg.isNullOrEmpty()) it.showErrorMsg(msg!!)
            else {
                when (e) {
                    is ApiException -> {
                        var msg : String? = (e as ApiException).message
                        it.showErrorMsg(msg?:"数据加载失败ヽ(≧Д≦)ノ")
                    }
                    is SocketTimeoutException -> it.showErrorMsg("服务器响应超时ヽ(≧Д≦)ノ")
                    is HttpException -> it.showErrorMsg("数据加载失败ヽ(≧Д≦)ノ")
                    else -> {
                        view.showErrorMsg("未知错误ヽ(≧Д≦)ノ")
                        LogUtils.e("MYERROR:" + e.toString())
                    }
                }
            }
        } ?: return
    }
}
