package com.weidingqiang.rxqweklibrary.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

/**
 * 作者：weidingqiang on 2018/3/28 21:05
 * 邮箱：weidingqiang@163.com
 */
class ProgressImageView : AppCompatImageView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun start() {
        val animationDrawable = drawable as AnimationDrawable
        //开始动画
        animationDrawable.start()
    }

    fun stop() {
        val animationDrawable = drawable as AnimationDrawable
        //停止动画
        animationDrawable.stop()
    }
}