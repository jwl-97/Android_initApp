package com.ljw.initapp.util

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.ljw.initapp.R

class BaseApplication : Application() {
    private var progressDialog: AppCompatDialog? = null

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    fun progressON(activity: Activity?, message: String?) {
        if (activity == null || activity.isFinishing) return

        val isShowing = progressDialog?.isShowing ?: false
        if (progressDialog != null && isShowing) {
            progressSET(message)
        } else {
            progressDialog = AppCompatDialog(activity)
            progressDialog?.setCancelable(false)
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog?.setContentView(R.layout.progress_loding)
            progressDialog?.show()
        }

        val imgLoadingFrame = progressDialog?.findViewById<View>(R.id.iv_frame_loading) as ImageView
        val frameAnimation = imgLoadingFrame.background as AnimationDrawable
        imgLoadingFrame.post { frameAnimation.start() }
        val tvProgressMessage =
            progressDialog?.findViewById<View>(R.id.tv_progress_message) as TextView
        if (!TextUtils.isEmpty(message)) {
            tvProgressMessage.text = message
        }
    }

    fun progressSET(activity: Activity?, message: String?) {
        if (activity == null || activity.isFinishing) return

        val isShowing = progressDialog?.isShowing ?: false
        if (progressDialog != null && isShowing) {
            progressSET(message)
        } else {
            return
        }
    }

    fun progressSET(message: String?) {
        val isShowing = progressDialog?.isShowing ?: false
        if (progressDialog == null || !isShowing) return

        val tvProgressMessage =
            progressDialog?.findViewById<View>(R.id.tv_progress_message) as TextView
        if (!TextUtils.isEmpty(message)) {
            tvProgressMessage.text = message
        }
    }

    fun progressOFF() {
        val isShowing = progressDialog?.isShowing ?: false
        if (progressDialog != null && isShowing) progressDialog?.dismiss()
    }

    companion object {
        private var baseApplication: BaseApplication? = null

        fun getInstance(): BaseApplication {
            if (baseApplication == null) {
                baseApplication = BaseApplication()
            }
            return baseApplication as BaseApplication
        }
    }
}