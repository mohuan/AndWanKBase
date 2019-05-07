package com.weidingqiang.wanbasek.model.bean

data class FeedArticleListData(var curPage: Int,var datas: List<FeedArticleData>,var offset: Int,var isOver: Boolean,
                               var pageCount: Int,var size: Int,var total: Int)
