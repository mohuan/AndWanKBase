package com.weidingqiang.wanbasek.model.http.api

import com.weidingqiang.wanbasek.model.http.response.HttpResponse
import com.weidingqiang.wanbasek.model.bean.FeedArticleListData
import com.weidingqiang.wanbasek.model.bean.UserVO
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * 作者：weidingqiang on 2018/3/28 14:52
 * 邮箱：weidingqiang@163.com
 */
interface QBaseApis{
    //登陆
    @FormUrlEncoded
    @POST("/teacher/login")
    fun postLogin(@Field("username") username: String, @Field("password") password: String): Flowable<HttpResponse<UserVO>>


    //首页数据
    @GET("/article/list/{num}/json")
    fun getFeedArticleList(@Path("num") num: Int): Flowable<HttpResponse<FeedArticleListData>>


}