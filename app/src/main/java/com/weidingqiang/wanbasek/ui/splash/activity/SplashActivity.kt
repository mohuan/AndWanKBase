package com.weidingqiang.wanbasek.ui.splash.activity

import android.content.Intent
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import cn.bingoogolapple.bgabanner.BGALocalImageSize
import com.weidingqiang.wanbasek.R
import com.weidingqiang.wanbasek.base.RootActivity
import com.weidingqiang.wanbasek.ui.main.activity.MainActivity
import com.weidingqiang.wanbasek.ui.splash.contract.SplashContract
import com.weidingqiang.wanbasek.ui.splash.presenter.SplashPresenter
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by weidingqiang
 */

/**
 * 1.路由
 * 2.音频
 * 3.视频
 */

/**
 * 已存在的功能
 * 1.log
 * 2.autolayout 适配
 * 3.qmui皮肤
 * 4.bga欢迎页面
 * 5.多状态
 * 6.rxjava dagger
 * 7.walle打包
 * 8.fragmentation管理
 * 9.utilcodex工具类
 * 10.BaseRecyclerViewAdapterHelper
 * 11.权限
 * 12.rxbinding
 * 13.全局参数
 * 14.topbar  bottombar  自定义组件
 * 15.webview
 * 16.列表
 * 17.图片加载 圆角图片
 * 18.弹框
 * 19.下载
 * 20.升级app
 */

/**
 * 待完成事项，继续升级框架
 * 1.新增mvvm框架
 */

class SplashActivity : RootActivity<SplashPresenter>(), SplashContract.View {

//    @BindView(R.id.welcome_bg)
//    internal var welcome_bg: LinearLayout? = null
//
//    @BindView(R.id.banner_guide)
//    internal var banner_guide: RelativeLayout? = null
//
//    @BindView(R.id.banner_guide_background)
//    internal var mBackgroundBanner: BGABanner? = null
//
//    @BindView(R.id.banner_guide_foreground)
//    internal var mForegroundBanner: BGABanner? = null

    override fun getLayout(): Int = R.layout.activity_splash

    override fun initPresenter() = mPresenter.attachView(this)

    override fun initInject() = activityComponent.inject(this)


    override fun initEventAndData() {
        //屏蔽安装点击的问题
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }


        //设置全屏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //判断第一次进入
        val shared = getSharedPreferences("is", MODE_PRIVATE)
        val isfer = shared.getBoolean("isfer", true)
        //        boolean isfer = true;
        val editor = shared.edit()
        if (isfer) {
            //第一次

            editor.putBoolean("isfer", false)
            editor.commit()

            banner_guide!!.visibility = View.VISIBLE
            welcome_bg!!.visibility = View.GONE

            setListener()
            processLogic()
        } else {
            //第二次进入跳转
            welcome_bg!!.visibility = View.VISIBLE

            val animation = AnimationUtils.loadAnimation(this, R.anim.app_start_anim)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                //动画完成
                override fun onAnimationEnd(animation: Animation) {
                    initApp(animation)
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            welcome_bg!!.animation = animation
        }
    }

    private fun setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        banner_guide_background!!.setEnterSkipViewIdAndDelegate(
            R.id.btn_guide_enter,
            R.id.tv_guide_skip,
            BGABanner.GuideDelegate {
                initApp(null)
            }
        )
    }

    private fun processLogic() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        val localImageSize = BGALocalImageSize(720, 1280, 320f, 640f)
        // 设置数据源
        banner_guide_background!!.setData(
            localImageSize, ImageView.ScaleType.CENTER_CROP,
            R.drawable.uoko_guide_background_1,
            R.drawable.uoko_guide_background_2,
            R.drawable.uoko_guide_background_3
        )

        banner_guide_foreground!!.setData(
            localImageSize, ImageView.ScaleType.CENTER_CROP,
            R.drawable.uoko_guide_foreground_1,
            R.drawable.uoko_guide_foreground_2,
            R.drawable.uoko_guide_foreground_3
        )
    }

    private fun initApp(animation: Animation?) {
        startActivity(MainActivity.newInstance(this))

//        if (mPresenter.isLogin) {
//            startActivity(MainActivity.newInstance(this))
//        } else {
////            startActivity(LoginActivity.newInstance(getApplicationContext()))
//        }
        finish()
    }

    override fun onResume() {
        super.onResume()

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        banner_guide_background!!.setBackgroundResource(android.R.color.white)
    }
}