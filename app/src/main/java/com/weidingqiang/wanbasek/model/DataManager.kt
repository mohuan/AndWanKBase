package com.weidingqiang.wanbasek.model

import com.weidingqiang.wanbasek.model.http.HttpHelper
import com.weidingqiang.wanbasek.model.http.response.HttpResponse
import com.weidingqiang.wanbasek.model.bean.FeedArticleListData
import com.weidingqiang.wanbasek.model.bean.UserVO
import com.weidingqiang.wanbasek.model.prefs.PreferencesHelper
import io.reactivex.Flowable

/**
 * 作者：weidingqiang on 2018/3/28 15:29
 * 邮箱：weidingqiang@163.com
 */
class DataManager(internal var mHttpHelper: HttpHelper,internal var preferencesHelper: PreferencesHelper) : HttpHelper,PreferencesHelper {

    override fun postLogin(username: String, password: String): Flowable<HttpResponse<UserVO>> =
        mHttpHelper.postLogin(username, password)

    override fun getFeedArticleList(num: Int): Flowable<HttpResponse<FeedArticleListData>> =
        mHttpHelper.getFeedArticleList(num)

    override var nightModeState: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override var loginStatus: Boolean
        get() = preferencesHelper.loginStatus
        set(value) {
            preferencesHelper.loginStatus = value
        }
}