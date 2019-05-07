package com.weidingqiang.wanbasek.app

import android.app.ActivityManager
import android.content.Context
import android.support.multidex.MultiDex
import com.weidingqiang.rxfiflibrary3_kotlin.app.BaseApplication
import com.weidingqiang.rxfiflibrary3_kotlin.app.CrashHandler
import com.weidingqiang.rxqweklibrary.service.InitializeService
import com.weidingqiang.wanbasek.di.component.AppComponent
import com.weidingqiang.wanbasek.di.component.DaggerAppComponent
import com.weidingqiang.wanbasek.di.module.AppModule
import com.weidingqiang.wanbasek.di.module.HttpModule

/**
 * 作者：weidingqiang on 2017/7/10 10:00
 * 邮箱：dqwei@iflytek.com
 */

class AppContext : BaseApplication() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        //在子线程中完成其他初始化
        InitializeService.start(this)

        //初始化崩溃信息
        initCrash()

    }

    private fun initCrash() {
        // 获取异常信息捕获类实例
        //        开发期间不要监听 稍后放开
        val crashHandler = CrashHandler.getInstance(applicationContext)

        crashHandler.setICrashHandlerListener(this)
        // 初始化
        crashHandler.init(applicationContext)
    }

    private fun getProcessName(context: Context): String? {
        val pid = android.os.Process.myPid()
        val mActivityManager = context
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in mActivityManager
            .runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }


    companion object {
        lateinit var instance: AppContext
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .httpModule(HttpModule())
            .appModule(AppModule(this))
            .build()
    }

//    companion object {
//
//        private val TAG = AppContext::class.java.simpleName
//
//        /**
//         * 获得当前app运行的AppContext
//         */
//        var instance: AppContext? = null
//            private set
//
//        var appComponent: AppComponent? = null
//
//        fun getAppComponent(): AppComponent? {
//            if (appComponent == null) {
//                appComponent = DaggerAppComponent.builder()
//                    .appModule(AppModule(instance))
//                    .httpModule(HttpModule())
//                    .build()
//            }
//            return appComponent
//        }
//    }
}
