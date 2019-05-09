package com.weidingqiang.wanbasek.ui.main.presenter

import com.weidingqiang.wanbasek.base.RxPresenter
import com.weidingqiang.wanbasek.model.DataManager
import com.weidingqiang.wanbasek.ui.main.contract.HomeContract
import com.weidingqiang.wanbasek.ui.main.contract.MainContract
import javax.inject.Inject

/**
 * 作者：weidingqiang on 2018/3/29 10:44
 * 邮箱：weidingqiang@163.com
 */
class HomePresenter @Inject
constructor(private val dataManager: DataManager) : RxPresenter<HomeContract.View>(),HomeContract.Presenter{


    val TAG = HomePresenter::class.java.simpleName

    override fun attachView(view: HomeContract.View) {
        super.attachView(view)
        registerEvent()
    }

    fun registerEvent(){

//        addRxBusSubscribe(Event.LogoutEvent::class.java, Consumer {  mView?.finishActivity() })

//        addSubscribe(RxBus.toFlowable(Event.LogoutEvent::class.java)
//                .compose(RxUtil.rxSchedulerHelper())
//                .subscribeWith(object : BaseSubscriber<Event.LogoutEvent>(mView) {
//                    override fun onSuccess(t: Event.LogoutEvent) {
//                        mView?.finishActivity()
//                    }
//                }))
    }

//    override fun getNewestVersion(version:String) {
//        addSubscribe(dataManager.getNewestVersion("11",version,"110000")
//                .compose(RxUtil.rxSchedulerHelper<HttpResponse<UpdateModel>>())
//                .compose(RxUtil.handleTestResult<UpdateModel>())
//                .subscribeWith(object : BaseSubscriber<UpdateModel>(mView) {
//                    override fun onSuccess(t: UpdateModel) {
//                        if (t.versionCode != version) {
//                            //启动更新
//                            mView?.getNewestVersionSuccess(t)
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        //错误信息不处理
//                    }
//                }))
//    }
}