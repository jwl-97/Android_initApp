package com.ljw.initapp.util

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.ljw.initapp.data.DialogData

open class BaseActivity : AppCompatActivity() {
    var customDialog: CustomAlertDialog? = null

    fun progressON() {
        BaseApplication.getInstance().progressON(this, null)
    }

    fun progressON(activity: Activity?) {
        BaseApplication.getInstance().progressON(activity, null)
    }

    fun progressON(message: String?) {
        BaseApplication.getInstance().progressON(this, message)
    }

    //TODO progress ing시 하단 메세지 작성 함수
    fun progressSET(message: String?) {
        BaseApplication.getInstance().progressSET(this, message)
    }

    fun progressOFF() {
        BaseApplication.getInstance().progressOFF()
    }

    //TODO type별 dialog 정의는 util > CustomAlertDialog에서 가능하다. 수드 예시를 참고해서 변경할 수 있다.
    fun showDialog(
        activity: Activity,
        type: DialogType,
        dialogData: DialogData?,
        customDialogMultiplelListener: CustomDialogMultiplelListener?
    ) {
        customDialog = CustomAlertDialog(activity, type, dialogData, customDialogMultiplelListener)
        customDialog?.show()
    }

    fun dismissDialog() {
        customDialog?.dismiss()
    }
}