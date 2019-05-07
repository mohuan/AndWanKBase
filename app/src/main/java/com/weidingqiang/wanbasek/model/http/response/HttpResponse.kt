package com.weidingqiang.wanbasek.model.http.response

/**
 * 作者：weidingqiang on 2018/3/28 14:57
 * 邮箱：weidingqiang@163.com
 */
class HttpResponse<T>{

    var msg : String? = null
    var code : Int = 0
    var data : T? = null
}