package com.weidingqiang.wanbasek.ui.main.activity

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import com.weidingqiang.wanbasek.R
import com.weidingqiang.wanbasek.base.BaseFragmentActivity
import com.weidingqiang.wanbasek.ui.main.contract.MainContract
import com.weidingqiang.wanbasek.ui.main.fragment.HomeFragment
import com.weidingqiang.wanbasek.ui.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.SupportFragment

class MainActivity : BaseFragmentActivity<MainPresenter>(), MainContract.View {

    private val mFragments = arrayOfNulls<SupportFragment>(3)

    override fun getLayout(): Int = R.layout.activity_main

    override fun initEventAndData() {
        var firstFragment: SupportFragment? = findFragment(HomeFragment::class.java)

        if (firstFragment == null) {
            mFragments[FIRST] = HomeFragment.newInstance()
            mFragments[SECOND] = HomeFragment.newInstance()
            mFragments[THIRD] = HomeFragment.newInstance()

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                mFragments[FIRST], mFragments[SECOND], mFragments[THIRD])
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = firstFragment
            mFragments[SECOND] = findFragment(HomeFragment::class.java)
            mFragments[THIRD] = findFragment(HomeFragment::class.java)
        }
        initView()

        bottomBar.setCurrentItem(0)
    }

    private fun initView() {
        bottomBar.setOnTabSelectedListener { position, prePosition ->
            showHideFragment(mFragments[position], mFragments[prePosition])
        }
    }

    override fun responeError(errorMsg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //退出
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return false
        }

        return super.onKeyDown(keyCode, event)
    }

    /**
     * 退出登录后 需要重新进入main时 调用， 在此需要清空数据
     *
     * @param intent
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        bottomBar.setCurrentItem(0)
    }

    override fun initInject() = activityComponent.inject(this)

    override fun initPresenter() = mPresenter.attachView(this)

    companion object {

        val TAG = MainActivity::class.java.simpleName

        val FIRST = 0
        val SECOND = 1
        val THIRD = 2

        val REQUEST_CODE_UPGRADE = 10

        /**
         * 打开新Activity
         *
         * @param context
         * @return
         */
        fun newInstance(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }


}
