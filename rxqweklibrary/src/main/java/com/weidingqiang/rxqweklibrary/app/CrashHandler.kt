package com.weidingqiang.rxfiflibrary3_kotlin.app

/**
 *
 *
 * Copyright: Copyright (c) 2012
 * Company: ZTE
 * Description:异常信息监听实现类，捕获异常信息并录入异常信息文件
 *
 * @Title CrashHandler.java
 * @Package com.zte.iptvclient.android.common
 * @version 1.0
 * @author LiBingWu6005000224
 * @date 2012-2-27
 */


import android.content.Context
import android.os.Build
import android.os.Debug
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.widget.Toast

import com.blankj.utilcode.util.StringUtils
import com.umeng.analytics.MobclickAgent
import com.weidingqiang.rxqweklibrary.app.AppConstants
import com.weidingqiang.rxqweklibrary.managers.AppManagers

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.Date


/**
 * 异常捕获监听实现类
 * @ClassName:CrashHandler
 * @Description: 异常捕获监听实现类
 * @date: 2012-2-27
 */
class CrashHandler private constructor(private val mctx: Context?) : Thread.UncaughtExceptionHandler {

    /** 异常捕获监听  */
    private var m_handlerDefault: Thread.UncaughtExceptionHandler? = null

    /** */
    private var minstanceCrashHandler: ICrashHandler? = null

    /** 异常文件目录对象  */
    private var m_fileDir: File? = null

    /** 异常文件对象  */
    private var m_fileRecord: File? = null

    init {
        m_strErrorFilePath = getApplicationExceptionFilePath(mctx)
    }

    /**
     *
     * 获取应用异常时日志存放路径
     * @date 2012-2-27
     * @return CrashHandler实例
     */
    private fun getApplicationExceptionFilePath(ctx: Context?): String {
        var strFilePath = AppConstants.LOGS_PATH

        val pkgName = ctx?.packageName
        // 初始化默认路径
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            // /storage/emulated/0/Android/data/com.fifedu.mhk/cache

            try {
                strFilePath = ctx?.externalCacheDir!!.path
            } catch (ex: Exception) {
                strFilePath = ctx?.cacheDir!!.path
            }

        } else {
            // /data/data/com.fifedu.mhk/cache
            strFilePath = ctx?.cacheDir!!.path
        }

        strFilePath += File.separator + "logs" + File.separator

        // 检查文件夹存不存在，如果不存在则创建
        val file = File(strFilePath)
        if (!file.exists()) {
            // 创建目录
            file.mkdirs()
        }

        return strFilePath
    }

    /**
     *
     * 这里写方法名
     *
     *
     * Description: 这里用一句话描述这个方法的作用
     *
     *
     * @date 2014年3月5日
     * @author Administrator
     * @param instance
     */
    fun setICrashHandlerListener(instance: ICrashHandler) {
        minstanceCrashHandler = instance
    }

    /**
     *
     * 初始化方法
     * @date 2012-2-27
     * @param context Context句柄
     */
    fun init(context: Context?) {
        if (null != context) {
            m_handlerDefault = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(this)

            if (!StringUtils.isEmpty(m_strErrorFilePath)) {
                // 创建异常文件目录
                m_fileDir = File(m_strErrorFilePath!!)
                m_fileDir!!.mkdirs()
            }
        }
    }

    override fun uncaughtException(thread: Thread, ex: Throwable?) {
        if (null == ex) {
            Log.w("uncaughtException", "null == ex")
            return
        }

        if (null != minstanceCrashHandler) {
            minstanceCrashHandler!!.onUncaughtExceptionOccured(thread, ex)
        }

        // 若异常未处理或为空
        if (!handleException(ex) && null != m_handlerDefault) {
            m_handlerDefault!!.uncaughtException(thread, ex)
        } else {
            try {
                // 线程等待3秒
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            //需要退出所有程序 暂时没想到方法 先注释掉
            //2017年07月07日15:36:19
            AppManagers.getInstance().killAllActivity()
            //
            //            //友盟推出
            MobclickAgent.onKillProcess(mctx)
            //2017年07月07日15:36:27

            // 退出应用程序
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }

    /**
     *
     * 异常处理方法
     * @date 2012-2-27
     * @param ex Throwable异常
     * @return 是否进行了处理
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (null == ex) {
            return false
        }

        // Log打印异常信息
        ex.printStackTrace()
        // 输出流，用于异常信息文件写入
        var dos: DataOutputStream? = null

        try {
            // 获取时间Data对象实例
            val date = Date()
            val simpleDateFormat = SimpleDateFormat(DATE_FORMATE)
            // 获取日期格式，用于文件名
            val strFormatDate = simpleDateFormat.format(date)
            if (!StringUtils.isEmpty(m_strErrorFilePath)) {
                // 创建文件实例
                m_fileRecord = File(m_strErrorFilePath + strFormatDate + "_crash.txt")
                // 创建异常信息存储文件
                if (m_fileRecord!!.createNewFile()) {
                    dos = DataOutputStream(FileOutputStream(m_fileRecord!!))
                    //系统信息
                    printSysInfo(dos)
                    //异常信息
                    ex.printStackTrace(PrintStream(dos))
                }
            }
        } catch (exFile: FileNotFoundException) {
            exFile.printStackTrace()
        } catch (exIO: IOException) {
            exIO.printStackTrace()
        } finally {
            // 关闭输出流
            if (null != dos) {
                try {
                    dos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        // 线程Toast提示异常信息
        object : Thread() {

            override fun run() {
                Looper.prepare()

                if (null != mctx) {
                    Toast.makeText(mctx, "很抱歉，程序出现异常，即将退出。", Toast.LENGTH_LONG).show()
                }
                Looper.loop()
            }
        }.start()

        return true
    }

    /**
     *
     * 打印系统信息
     * @date 2013-8-14
     * @param dos 输出流
     */
    private fun printSysInfo(dos: DataOutputStream) {
        try {
            //os信息（sdk版本、android版本等）
            dos.writeBytes("\n" + "=========================")
            dos.writeBytes("\n" + "Model: " + Build.MODEL)
            dos.writeBytes("\n" + "Version SDK Level: " + Build.VERSION.SDK)
            dos.writeBytes("\n" + "Version Release: " + Build.VERSION.RELEASE)
            dos.writeBytes("\n" + "=========================")
            dos.writeBytes("\n" + "NativeHeapSize: " + Debug.getNativeHeapSize())
            dos.writeBytes("\n" + "NativeHeapAllocatedSize: "
                    + Debug.getNativeHeapAllocatedSize())
            dos.writeBytes("\n" + "NativeHeapFreeSize: " + Debug.getNativeHeapFreeSize())
            dos.writeBytes("\n" + "=========================")

            //内存信息（总内存、可用内存）
            val strMemInfoFile = "/proc/meminfo"
            try {
                val fr = FileReader(strMemInfoFile)
                val localBufferedReader = BufferedReader(fr, 1024 * 2)

                while (true) {
                    var strMemInfoItem = localBufferedReader.readLine() ?: break
                    dos.writeBytes("\n" + strMemInfoItem)
                }


                localBufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //cpu信息内容（cpu频率、cpu核数、cpu占用率等）
            dos.writeBytes("\n" + "=========================")
            val strCpuInfoFile = "/proc/cpuinfo"

            try {
                val fr = FileReader(strCpuInfoFile)
                val localBufferedReader = BufferedReader(fr, 1024 * 2)

                while (true) {
                    var strCpuInfoItem = localBufferedReader.readLine() ?: break
                    dos.writeBytes("\n" + strCpuInfoItem)
                }

                localBufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //            dos.writeBytes("\n" + "=========================" );
            //            String strStatFile = "/proc/stat";
            //            String strStatInfoItem = "";
            //
            //            try
            //            {
            //                FileReader fr = new FileReader(strStatFile);
            //                BufferedReader localBufferedReader = new BufferedReader(fr, 1024*2);
            //                while ((strStatInfoItem = localBufferedReader.readLine()) != null)
            //                {
            //                    dos.writeBytes("\n" + strStatInfoItem );
            //                }
            //
            //                localBufferedReader.close();
            //            }
            //            catch (IOException e)
            //            {
            //                e.printStackTrace();
            //            }

            dos.writeBytes("\n" + "=========================" + "\n")
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    /**
     *
     * 某某某类
     * @ClassName:ICrashHandler
     * @Description: 这里用一句话描述这个类的作用
     * @date: 2014年3月5日
     */
    interface ICrashHandler {
        /**
         *
         * 这里写方法名
         *
         *
         * Description: 这里用一句话描述这个方法的作用
         *
         *
         * @date 2014年3月5日
         * @param thread
         * @param ex
         * @return
         */
        fun onUncaughtExceptionOccured(thread: Thread, ex: Throwable): Boolean
    }

    companion object {

        /** 异常日志文件路径  */
        private var m_strErrorFilePath: String? = null

        /** 时间格式  */
        private val DATE_FORMATE = "yyyy-MM-dd_HHmm"

        /** CrashHandler实例  */
        private var m_instance: CrashHandler? = null

        /**
         *
         * 获取CrashHandler实例
         * @date 2012-2-27
         * @return CrashHandler实例
         */
        fun getInstance(ctx: Context): CrashHandler {
            if (null == m_instance) {
                m_instance = CrashHandler(ctx)
            }
            return m_instance as CrashHandler
        }
    }
}
