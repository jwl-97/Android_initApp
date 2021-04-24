package com.ljw.initapp.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUnit private constructor() {
    var preIsLogin: Boolean
        get() = mPref.getBoolean(PREF_LOGIN, false)
        set(isLogin) {
            val edit = mPref.edit()
            edit.putBoolean(PREF_LOGIN, isLogin)
            edit.apply()
        }

    companion object {
        private lateinit var mPref: SharedPreferences
        private var mInstance: PreferenceUnit? = null

        private const val PREF_NAME = "caroPrefName"
        private const val PREF_LOGIN = "caroPrefLogin"

        fun init(context: Context) {
            mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        fun getInstance(): PreferenceUnit {
            if (mInstance == null) {
                mInstance = PreferenceUnit()
            }
            return mInstance as PreferenceUnit
        }
    }
}
