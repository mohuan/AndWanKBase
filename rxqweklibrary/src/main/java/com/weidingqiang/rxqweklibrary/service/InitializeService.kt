package com.weidingqiang.rxqweklibrary.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Environment
import com.blankj.utilcode.util.DeviceUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.weidingqiang.rxqweklibrary.BuildConfig
import com.weidingqiang.rxqweklibrary.R
import com.weidingqiang.rxqweklibrary.app.AppConfig
import com.weidingqiang.rxqweklibrary.app.AppConstants
import com.weidingqiang.rxqweklibrary.utils.log.TxtFormatStrategy

/**
 * 作者：weidingqiang on 2018/3/27 14:27
 * 邮箱：weidingqiang@163.com
 */
class InitializeService:IntentService("InitializeService"){

    companion object {

        private val ACTION_INIT = "initApplication"

        fun start(context : Context){
            val intent = Intent(context, InitializeService::class.java)
            intent.action = ACTION_INIT
            context.startService(intent)
        }
    }

    override fun onHandleIntent(p0: Intent?) {
        val action = p0?.action
        if (ACTION_INIT == action) {
            initApplication()
        }
    }

    fun initApplication(){
        //初始化需要的文件夹
        initFinder()

        //初始化log
        initlog()

        //初始化其他信息
        init()
    }

    fun initFinder(){
        var cachePath : String?

        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            // /storage/emulated/0/Android/data/com.fifedu.mhk/cache

            try {
                cachePath = externalCacheDir!!.path
            } catch (ex: Exception) {
                cachePath = cacheDir.path
            }

        } else {
            // /data/data/com.fifedu.mhk/cache
            cachePath = cacheDir.path
        }
        AppConstants.initPath(cachePath)
    }

    private fun initlog() {
        // 初始化日志写文件的工具
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(AndroidLogAdapter(PrettyFormatStrategy.newBuilder().
                tag(getString(R.string.app_name)).build()));
        }
        //把log存到本地
        Logger.addLogAdapter(DiskLogAdapter(TxtFormatStrategy.newBuilder().
            tag(getString(R.string.app_name)).build(getPackageName(), getString(R.string.app_name))));

        Logger.d( "AppContext Created");
        Logger.d( "Product Model: " + DeviceUtils.getModel() + "\nApi Level: "
                + DeviceUtils.getSDKVersionName() + "\nVersion: " + DeviceUtils.getSDKVersionCode());
    }

    private fun init() {

        //        LogUtil.debug(TAG,"配置多态布局");
        //        PageStateLayout.Builder.setErrorView(R.layout.page_state_error)
        //                .setEmptyView(R.layout.page_state_empty);
        //初始化volley

        // 初始化音频录制工具
//        RecorderManager.createInstance(this)

        AppConfig.getAppConfig(this)

    }
}