package com.weidingqiang.wanbasek.di.module

import android.app.Activity
import android.support.v4.app.Fragment
import com.weidingqiang.wanbasek.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * 作者：weidingqiang on 2018/3/28 13:58
 * 邮箱：weidingqiang@163.com
 */
@Module
class FragmentModule(val fragment: Fragment){

    @Provides
    @FragmentScope
    fun provideActivity():Activity = fragment.requireActivity()
}