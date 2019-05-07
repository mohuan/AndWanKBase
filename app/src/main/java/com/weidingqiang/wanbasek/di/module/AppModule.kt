package com.weidingqiang.wanbasek.di.module

import com.weidingqiang.wanbasek.model.http.HttpHelper
import com.weidingqiang.wanbasek.model.http.RetrofitHelper
import com.weidingqiang.wanbasek.app.AppContext
import com.weidingqiang.wanbasek.model.DataManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 作者：weidingqiang on 2018/3/28 14:07
 * 邮箱：weidingqiang@163.com
 */
@Module
class AppModule(val application: AppContext) {

    @Provides
    @Singleton
    fun provideContext(): AppContext = application

    @Provides
    @Singleton
    internal fun provideHttpHelper(retrofitHelper: RetrofitHelper): HttpHelper = retrofitHelper


    @Provides
    @Singleton
    internal fun provideDataManager(httpHelper: HttpHelper): DataManager = DataManager(httpHelper)
}