package com.ljw.initapp.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.ljw.initapp.R
import com.ljw.initapp.data.DialogData
import kotlinx.android.synthetic.main.dialog_custom.*

enum class DialogType() { NORMAL }

class CustomAlertDialog(
    context: Context,
    private val type: DialogType,
    private val dialogData: DialogData?,
    private var customDialogListener: CustomDialogMultiplelListener?
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (type) {
            DialogType.NORMAL -> {
                setContentView(R.layout.dialog_custom)

                initViewSetting()

                dialogNormalTitleTextView.text = dialogData?.title
                dialogNormalContentTextView.text = dialogData?.content

                if (customDialogListener != null) {
                    dialogNormalConfirmButton.setOnClickListener {
                        dismiss()
                        customDialogListener!!.onCloseClicked()
                    }
                } else {
                    dialogNormalConfirmButton.setOnClickListener { dismiss() }
                }
            }
        }

        setCancelable(false)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initViewSetting() {
        window?.setGravity(Gravity.BOTTOM)

        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val lp = window!!.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        lp.dimAmount = 0.6f
        window!!.attributes = lp
    }
}