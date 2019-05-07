package com.weidingqiang.wanbasek.base

import android.view.View
import android.view.ViewGroup
import com.weidingqiang.rxfiflibrary3_kotlin.base.BasePresenter
import com.weidingqiang.rxqweklibrary.widget.ProgressImageView
import com.weidingqiang.wanbasek.R

/**
 * 作者：weidingqiang on 2018/3/28 20:59
 * 邮箱：weidingqiang@163.com
 */
abstract class RootActivity<T : BasePresenter<*>> : BaseActivity<T>() {

    private var ivLoading: ProgressImageView? = null
    private var viewError: View? = null
    private var viewEmpty: View? = null
    private var viewLoading: View? = null
    private var viewMain: ViewGroup? = null
    private var mParent: ViewGroup? = null

    private var mErrorResource = R.layout.view_error
    private val mEmptyResource = R.layout.view_empty
    protected var currentState = STATE_MAIN
    private var isErrorViewAdded = false

    override fun initEventAndData() {
        viewMain = findViewById<ViewGroup>(R.id.view_main)
//        viewMain = findViewById(R.id.view_main) as ViewGroup
        if (viewMain == null) {
            throw IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'view_main'.")
        }
        if (viewMain!!.parent !is ViewGroup) {
            throw IllegalStateException(
                    "view_main's ParentView should be a ViewGroup.")
        }
        mParent = viewMain!!.parent as ViewGroup
        View.inflate(mContext, R.layout.view_progress, mParent)
        viewLoading = mParent!!.findViewById(R.id.view_loading)
        ivLoading = viewLoading!!.findViewById<ProgressImageView>(R.id.iv_progress)
        viewLoading!!.visibility = View.GONE
        viewMain!!.visibility = View.VISIBLE
    }

    override fun stateError() {
        if (currentState == STATE_ERROR)
            return
        if (!isErrorViewAdded) {
            isErrorViewAdded = true
            View.inflate(mContext, mErrorResource, mParent)
            viewError = mParent!!.findViewById(R.id.view_error)
            if (viewError == null) {
                throw IllegalStateException(
                        "A View should be named 'view_error' in ErrorLayoutResource.")
            }
        }
        hideCurrentView()
        currentState = STATE_ERROR
        viewError!!.visibility = View.VISIBLE
    }

    override fun stateEmpty() {
        if (currentState == STATE_EMPTY)
            return
        if (!isErrorViewAdded) {
            isErrorViewAdded = true
            View.inflate(mContext, mEmptyResource, mParent)
            viewEmpty = mParent!!.findViewById(R.id.view_empty)
            //            mParent.findViewById(R.id.btn_back_mian).setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View v) {
            //                    RxBus.getDefault().post(new DoExciseEvent(DoExciseEvent.DOEXCISE));
            //                }
            //            });
            if (viewEmpty == null) {
                throw IllegalStateException(
                        "A View should be named 'view_error' in ErrorLayoutResource.")
            }
        }
        hideCurrentView()
        currentState = STATE_EMPTY
        viewEmpty!!.visibility = View.VISIBLE
    }


    override fun stateLoading() {
        if (currentState == STATE_LOADING)
            return
        hideCurrentView()
        currentState = STATE_LOADING
        viewLoading!!.visibility = View.VISIBLE
        ivLoading!!.start()
    }

    override fun stateMain() {
        if (currentState == STATE_MAIN)
            return
        hideCurrentView()
        currentState = STATE_MAIN
        viewMain!!.visibility = View.VISIBLE
    }

    private fun hideCurrentView() {
        when (currentState) {
            STATE_MAIN -> viewMain!!.visibility = View.GONE
            STATE_LOADING -> {
                ivLoading!!.stop()
                viewLoading!!.visibility = View.GONE
            }
            STATE_ERROR -> if (viewError != null) {
                viewError!!.visibility = View.GONE
            }
        }
    }

    fun setErrorResource(errorLayoutResource: Int) {
        this.mErrorResource = errorLayoutResource
    }

    companion object {

        val STATE_MAIN = 0x00
        private val STATE_LOADING = 0x01
        private val STATE_ERROR = 0x02
        private val STATE_EMPTY = 0x03
    }
}