package com.weidingqiang.wanbasek.di.component

import android.app.Activity
import com.weidingqiang.wanbasek.di.module.ActivityModule
import com.weidingqiang.wanbasek.di.scope.ActivityScope
import dagger.Component

/**
 * 作者：weidingqiang on 2018/3/28 15:36
 * 邮箱：weidingqiang@163.com
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun getActivity(): Activity

//    fun inject(mainActivity: MainActivity)

}