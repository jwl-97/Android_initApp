package com.ljw.initapp

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewTreeObserver
import com.ljw.initapp.data.ApplicationClass
import com.ljw.initapp.util.BaseActivity
import com.ljw.initapp.util.PreferenceUnit
import com.ljw.initapp.util.Stack

class SplashActivity : BaseActivity() {
    private lateinit var applicationClass: ApplicationClass
    private lateinit var mFCMToken: String

    private var mToken: String? = null
    private var appVersion: String? = null

    //TODO 네트워크 통신
//    private var retrofitClient = RetrofitClientV2.getInstance()
//    private var iMyService: RetrofitServiceV2 =
//        (retrofitClient as Retrofit).create(RetrofitServiceV2::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_splash, null, false)
        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })

        setContentView(view)

        applicationClass = application as ApplicationClass
//        FirebaseApp.initializeApp(this)

        appGoOn()
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private fun getCurrentVersion(context: Context): String? {
        return context.packageManager.getPackageInfo(context.packageName, 0).versionName
    }

    /** 서버에서 내려온 버전과 현재 앱 버전을 비교 */
    private fun needUpdate(currentVersion: String?, getVersion: String?): Boolean {
        if (currentVersion == null || getVersion == null) return false
        val currentSplit = currentVersion.split(".").toTypedArray()
        val getSplit = getVersion.split(".").toTypedArray()

        var currentInteger = ""
        var getInteger = ""

        for (i in 0..1) {
            currentInteger += currentSplit[i]
            getInteger += getSplit[i]
        }

        if (getInteger.toInt() > currentInteger.toInt()) return true

        return false
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private fun checkLogin(): Boolean {
        mToken = null

        val applicationClass = application as ApplicationClass
        val isLogin = PreferenceUnit.getInstance().preIsLogin
        mToken = applicationClass.apiToken

        if (!applicationClass.activityDataStack.isEmpty()) {
            applicationClass.activityDataStack.clear()
            applicationClass.activityDataStack = Stack()
        }

        applicationClass.newActivityDataStack()

        return mToken != null && isLogin
    }

    /** 강제 업데이트 여부 체크 후 플레이스토어 랜딩 OR 메인 화면 랜딩 */
    private fun appGoOn() {
        if (needUpdate(getCurrentVersion(this), appVersion)) {
            AlertDialog.Builder(this@SplashActivity)
                .setMessage("최신 버전으로 업데이트가 필요합니다.")
                .setPositiveButton("확인") { _, _ ->
                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("market://details?id=$packageName")
                        startActivity(intent)
                        finish()
                    } catch (anfe: ActivityNotFoundException) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data =
                            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                        startActivity(intent)
                        finish()
                    }
                }
                .setNegativeButton("취소") { _, _ ->
                    finish()
                }
                .show()
        } else {
            PreferenceUnit.init(applicationContext)

            mFCMToken = ""
            //TODO firebase token 얻어오는 부분 - 추후에 구현
//            FirebaseInstanceId.getInstance().instanceId
//                .addOnCompleteListener(OnCompleteListener { task ->
//                    if (!task.isSuccessful) {
//                        DLog.w("FCM", "getInstanceId failed", task.exception)
//                        return@OnCompleteListener
//                    }
//
//                    mFCMToken = task.result!!.token
            if (checkLogin()) {
                //TODO
            } else {
                goToOtherActivity(MainActivity::class.java)
            }
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////

    private fun goToOtherActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }
}
