package com.weidingqiang.wanbasek.model

import com.weidingqiang.wanbasek.model.http.HttpHelper
import com.weidingqiang.wanbasek.model.http.response.HttpResponse
import com.weidingqiang.wanbasek.model.bean.FeedArticleListData
import com.weidingqiang.wanbasek.model.bean.UserVO
import io.reactivex.Flowable

/**
 * 作者：weidingqiang on 2018/3/28 15:29
 * 邮箱：weidingqiang@163.com
 */
class DataManager(internal var mHttpHelper: HttpHelper) : HttpHelper {

    override fun postLogin(username: String, password: String): Flowable<HttpResponse<UserVO>> =
        mHttpHelper.postLogin(username, password)

    override fun getFeedArticleList(num: Int): Flowable<HttpResponse<FeedArticleListData>> =
        mHttpHelper.getFeedArticleList(num)
}