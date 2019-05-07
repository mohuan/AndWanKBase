package com.weidingqiang.wanbasek.di.component

import android.app.Activity
import com.weidingqiang.wanbasek.di.module.FragmentModule
import com.weidingqiang.wanbasek.di.scope.FragmentScope
import dagger.Component

/**
 * 作者：weidingqiang on 2018/3/28 15:37
 * 邮箱：weidingqiang@163.com
 */
@FragmentScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun getActivity(): Activity

//    fun inject(arrangeMainFragment: ArrangeMainFragment)


}