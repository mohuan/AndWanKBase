package com.weidingqiang.wanbasek.di.module

import android.app.Activity
import com.weidingqiang.wanbasek.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * 作者：weidingqiang on 2018/3/28 13:55
 * 邮箱：weidingqiang@163.com
 */
@Module
class ActivityModule(val activity: Activity){

    @Provides
    @ActivityScope
    fun provideActivity():Activity = activity
}