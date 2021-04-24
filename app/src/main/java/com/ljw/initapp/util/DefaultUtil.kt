package com.ljw.initapp.util

import android.os.Build
import android.view.View

class DefaultUtil private constructor() {
    fun initStatusbar(view: View) {
        var shouldChangeStatusBarTintToDark = true
        view.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldChangeStatusBarTintToDark) {
                // 상태바의 아이콘 생각을 어두운 색으로 사용
                view.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                // 상태바의 아이콘 색상을 밝은 색으로 사용
                view.systemUiVisibility = 0
            }
        } else {
            shouldChangeStatusBarTintToDark = false
        }
    }

    companion object {
        private var mInstance: DefaultUtil? = null
        fun init() {
            mInstance = DefaultUtil()
        }

        fun getInstance(): DefaultUtil {
            if (mInstance == null) mInstance = DefaultUtil()
            return mInstance as DefaultUtil
        }
    }
}