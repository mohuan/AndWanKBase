package com.weidingqiang.wanbasek.model.prefs

import android.content.Context
import android.content.SharedPreferences
import com.weidingqiang.wanbasek.api.Constants
import com.weidingqiang.wanbasek.app.AppContext
import javax.inject.Inject

/**
 * @author: weidingqiang
 * @date: 2017/4/21
 * @description:
 */

class ImplPreferencesHelper @Inject
constructor() : PreferencesHelper {

    private val mSPrefs: SharedPreferences

    override var nightModeState: Boolean
        get() = true
        set(state) {}

    override var loginStatus: Boolean
        get() = mSPrefs.getBoolean(Constants.LOGIN_STATUS, false)
        set(isLogin) = mSPrefs.edit().putBoolean(Constants.LOGIN_STATUS, isLogin).apply()

    init {
        mSPrefs = AppContext.instance.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    companion object {

        private val DEFAULT_NIGHT_MODE = false
        private val DEFAULT_NO_IMAGE = false
        private val DEFAULT_AUTO_SAVE = true

        private val DEFAULT_LIKE_POINT = false
        private val DEFAULT_VERSION_POINT = false
        private val DEFAULT_MANAGER_POINT = false

        //    private static final int DEFAULT_CURRENT_ITEM = Constants.TYPE_ZHIHU;

        private val SHAREDPREFERENCES_NAME = "my_sp"
    }
}
