package com.ljw.initapp.util

import android.util.Log
import com.ljw.initapp.BuildConfig

object DLog {
    private var isDebug = BuildConfig.ON

    /**
     * Log Level Error
     */
    fun e(tag: String, message: String?) {
        if (isDebug) Log.e(tag, buildLogMsg(message))
    }

    /**
     * Log Level Warning
     */
    fun w(tag: String, message: String?) {
        if (isDebug) Log.w(tag, buildLogMsg(message))
    }

    /**
     * Log Level Information
     */
    fun i(tag: String, message: String?) {
        if (isDebug) Log.i(tag, buildLogMsg(message))
    }

    /**
     * Log Level Debug
     */
    fun d(tag: String, message: String?) {
        if (isDebug) Log.d(tag, buildLogMsg(message))
    }

    /**
     * Log Level Verbose
     */
    fun v(tag: String, message: String?) {
        if (isDebug) Log.v(tag, buildLogMsg(message))
    }

    private fun buildLogMsg(message: String?): String {
        val ste = Thread.currentThread().stackTrace[4]
        val sb = StringBuilder()
        sb.append("[")
        sb.append(ste.fileName.replace(".java", ""))
        sb.append("::")
        sb.append(ste.methodName)
        sb.append("]")
        sb.append(message)
        return sb.toString()
    }
}