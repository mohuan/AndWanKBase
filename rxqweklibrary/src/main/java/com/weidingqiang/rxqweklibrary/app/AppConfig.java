package com.weidingqiang.rxqweklibrary.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.blankj.utilcode.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 应用需要初始化的方法管理
 * @author weidingqiang
 *
 * 2015年7月28日
 */
public class AppConfig {
    public static final String TAG = "AppConfig";

    private Context mContext;
    private static AppConfig appConfig;

    private String[] mAppFolders = {

            AppConstants.CACHE_PATH,
            AppConstants.PARENT_FOLD_PATH,

            AppConstants.DOWNLOAD_PATH,
            AppConstants.LOGS_PATH,

            AppConstants.RECORD_DOWNLOAD_PATH,
            AppConstants.FILE_DOWNLOAD_PATH
    };

    private AppConfig(){
//        initAppFolder();
    }

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }


    /**
     * 初始化应用文件目录
     */
    public void initAppFolder(){
        for(String str:mAppFolders){
            FileUtils.createOrExistsDir(str);
        }
    }



}
