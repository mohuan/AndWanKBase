package com.weidingqiang.wanbasek.model.http

import com.weidingqiang.wanbasek.model.http.response.HttpResponse
import com.weidingqiang.wanbasek.model.bean.FeedArticleListData
import com.weidingqiang.wanbasek.model.bean.UserVO
import com.weidingqiang.wanbasek.model.http.api.QBaseApis
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * 作者：weidingqiang on 2018/3/28 15:24
 * 邮箱：weidingqiang@163.com
 */
class RetrofitHelper @Inject
constructor(private val qBaseApis: QBaseApis) : HttpHelper {

    override fun postLogin(username: String, password: String): Flowable<HttpResponse<UserVO>> =
        qBaseApis.postLogin(username, password)


    override fun getFeedArticleList(num: Int): Flowable<HttpResponse<FeedArticleListData>> =
        qBaseApis.getFeedArticleList(num)

}