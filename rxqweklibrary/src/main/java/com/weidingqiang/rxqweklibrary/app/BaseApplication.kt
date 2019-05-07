package com.weidingqiang.rxfiflibrary3_kotlin.app

/**
 * 作者：weidingqiang on 2018/2/25 14:53
 * 邮箱：dqwei@iflytek.com
 */
import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build

import com.blankj.utilcode.util.Utils
import com.squareup.leakcanary.LeakCanary

/**
 * Created by weidingqiang on 16/3/15.
 */
open class BaseApplication : Application(), CrashHandler.ICrashHandler {

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        Utils.init(this)

        _context = applicationContext
        _resource = _context!!.resources

    }

    /**
     *
     * 全局未处理异常的处理
     *
     *
     * Description: 全局未处理异常的处理，界面层可以根据需要定制自己的业务，例如上传异常日志等。
     *
     *
     * @date 2014年3月5日
     * @param thread 线程信息
     * @param ex 异常等信息
     * @return 返回true代表事件被消耗掉了，底层不再处理。
     */
    fun onGlobalUncaughtExceptionOccured(thread: Thread, ex: Throwable): Boolean {
        return false
    }

    override fun onUncaughtExceptionOccured(thread: Thread, ex: Throwable): Boolean {
        return onGlobalUncaughtExceptionOccured(thread, ex)
    }

    companion object {

        private val PREF_NAME = "creativelocker.pref"

        var _context: Context? = null
        var _resource: Resources? =null

        @Synchronized
        fun context(): BaseApplication {
            return _context as BaseApplication
        }

        fun resources(): Resources? {
            return _resource
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        fun apply(editor: SharedPreferences.Editor) {
            /**
             * apply 异步提交
             * commit 同步提交
             * commit 会返回布尔值
             */
            editor.apply()

            //        if (sIsAtLeastGB) {
            //
            //        } else {
            //            editor.commit();
            //        }
        }

        operator fun set(key: String, value: Int) {
            val editor = preferences.edit()
            editor.putInt(key, value)
            apply(editor)
        }

        operator fun set(key: String, value: Boolean) {
            val editor = preferences.edit()
            editor.putBoolean(key, value)
            apply(editor)
        }

        operator fun set(key: String, value: String) {
            val editor = preferences.edit()
            editor.putString(key, value)
            apply(editor)
        }

        operator fun get(key: String, defValue: Boolean): Boolean {
            return preferences.getBoolean(key, defValue)
        }

        operator fun get(key: String, defValue: String): String? {
            return preferences.getString(key, defValue)
        }

        operator fun get(key: String, defValue: Int): Int {
            return preferences.getInt(key, defValue)
        }

        operator fun get(key: String, defValue: Long): Long {
            return preferences.getLong(key, defValue)
        }

        operator fun get(key: String, defValue: Float): Float {
            return preferences.getFloat(key, defValue)
        }

        val preferences: SharedPreferences
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            get() = context().getSharedPreferences(PREF_NAME,
                    Context.MODE_MULTI_PROCESS)

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        fun getPreferences(prefName: String): SharedPreferences {
            return context().getSharedPreferences(prefName,
                    Context.MODE_MULTI_PROCESS)
        }
    }
}