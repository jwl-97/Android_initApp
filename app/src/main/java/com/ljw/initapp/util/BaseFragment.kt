package com.ljw.initapp.util

import android.app.Activity
import androidx.fragment.app.Fragment
import com.ljw.initapp.data.DialogData

open class BaseFragment : Fragment() {
    var customDialog: CustomAlertDialog? = null

    fun progressON() {
        BaseApplication.getInstance().progressON(activity, null)
    }

    fun progressON(message: String?) {
        BaseApplication.getInstance().progressON(activity, message)
    }

    fun progressSET(message: String?) {
        BaseApplication.getInstance().progressSET(activity, message)
    }

    fun progressOFF() {
        BaseApplication.getInstance().progressOFF()
    }

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