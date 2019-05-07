package com.weidingqiang.wanbasek.di.component

import com.weidingqiang.wanbasek.di.module.AppModule
import com.weidingqiang.wanbasek.di.module.HttpModule
import com.weidingqiang.wanbasek.model.http.RetrofitHelper
import com.weidingqiang.wanbasek.app.AppContext
import com.weidingqiang.wanbasek.model.DataManager
import dagger.Component
import javax.inject.Singleton

/**
 * 作者：weidingqiang on 2018/3/28 14:05
 * 邮箱：weidingqiang@163.com
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, HttpModule::class))
interface AppComponent {

    val context: AppContext  // 提供App的Context

    val dataManager: DataManager //数据中心

    fun retrofitHelper(): RetrofitHelper   //提供http的帮助类
}