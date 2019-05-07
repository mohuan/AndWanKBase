package com.weidingqiang.wanbasek.model.http.response

/**
 * 作者：weidingqiang on 2018/3/28 14:57
 * 邮箱：weidingqiang@163.com
 */
class HttpResponseCode{

    var msg : String? = null
    var code : Int = 0

    override fun toString(): String {
        return "{\"msg\":\"${msg}\", \"code\":${code}}"
    }
}