package com.weidingqiang.wanbasek.model.http

import com.weidingqiang.wanbasek.model.http.response.HttpResponse
import com.weidingqiang.wanbasek.model.bean.FeedArticleListData
import com.weidingqiang.wanbasek.model.bean.UserVO
import io.reactivex.Flowable

/**
 * 作者：weidingqiang on 2018/3/28 15:22
 * 邮箱：weidingqiang@163.com
 */
interface HttpHelper{


    fun postLogin(username: String, password: String): Flowable<HttpResponse<UserVO>>

    fun getFeedArticleList(num: Int): Flowable<HttpResponse<FeedArticleListData>>

}