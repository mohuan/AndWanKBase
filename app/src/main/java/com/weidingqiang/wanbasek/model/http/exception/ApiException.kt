package com.weidingqiang.wanbasek.model.http.exception

/**
 * 作者：weidingqiang on 2018/3/28 14:54
 * 邮箱：weidingqiang@163.com
 */
class ApiException : Exception{
    var code : Int = 0

    constructor(msg:String) : super(msg){}

    constructor(msg: String,code:Int) : super(msg){
        this.code = code
    }
}