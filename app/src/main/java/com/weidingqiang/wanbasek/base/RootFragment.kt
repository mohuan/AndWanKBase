package com.weidingqiang.wanbasek.base

import android.view.View
import android.view.ViewGroup
import com.weidingqiang.rxfiflibrary3_kotlin.base.BasePresenter
import com.weidingqiang.rxqweklibrary.widget.ProgressImageView
import com.weidingqiang.wanbasek.R

/**
 * 作者：weidingqiang on 2018/3/28 21:14
 * 邮箱：weidingqiang@163.com
 */
abstract class RootFragment<T : BasePresenter<*>> : BaseFragment<T>() {

    private var ivLoading: ProgressImageView? = null
    private var viewError: View? = null
    private var viewEmpty: View? = null
    private var viewEmptyReport: View? = null
    private var viewLoading: View? = null
    private var viewMain: ViewGroup? = null
    private var mParent: ViewGroup? = null

    private var mErrorResource = R.layout.view_error

    private val mEmptyResource = R.layout.view_empty


    private var currentState = STATE_MAIN
    private var isErrorViewAdded = false
    private var isEmptyViewAdded = false

    override fun initEventAndData() {
        if (view == null)
            return
        viewMain = view!!.findViewById<ViewGroup>(R.id.view_main)
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
        //        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
        //            View.inflate(mContext, R.layout.view_progress, mParent);
        //        }
        //        else {
        //            View.inflate(mContext, R.layout.view_progress_19, mParent);
        //        }

        viewLoading = mParent!!.findViewById(R.id.view_loading)
        ivLoading = viewLoading!!.findViewById<ProgressImageView>(R.id.iv_progress)

        viewLoading!!.visibility = View.GONE
        viewMain!!.visibility = View.VISIBLE
    }

    override fun stateError() {
        if (currentState == STATE_REPORT_EMPTY)
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

    override fun stateEmpty() {
        if (currentState == STATE_EMPTY)
            return
        if (!isEmptyViewAdded) {
            isEmptyViewAdded = true
            View.inflate(mContext, mEmptyResource, mParent)
            viewEmpty = mParent!!.findViewById(R.id.view_empty)
            if (viewEmpty == null) {
                throw IllegalStateException(
                        "A View should be named 'view_error' in ErrorLayoutResource.")
            }
        }
        hideCurrentView()
        currentState = STATE_EMPTY
        viewEmpty!!.visibility = View.VISIBLE
    }

    private fun hideCurrentView() {
        when (currentState) {
            STATE_MAIN -> viewMain!!.visibility = View.GONE
            STATE_LOADING -> {
                ivLoading!!.stop()
                viewLoading!!.visibility = View.GONE
            }
            STATE_ERROR -> {
                if (viewError != null) {
                    viewError!!.visibility = View.GONE
                }
                if (viewEmpty != null) {
                    viewEmpty!!.visibility = View.GONE
                }
            }
            STATE_EMPTY -> if (viewEmpty != null) {
                viewEmpty!!.visibility = View.GONE
            }
        }
    }

    fun setErrorResource(errorLayoutResource: Int) {
        this.mErrorResource = errorLayoutResource
    }

    companion object {

        private val STATE_MAIN = 0x00
        private val STATE_LOADING = 0x01
        private val STATE_ERROR = 0x02
        private val STATE_EMPTY = 0x03
        private val STATE_REPORT_EMPTY = 0x04
        private val STATE_ORDER_EMPTY = 0x05
    }
}