package com.ljw.initapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.ljw.initapp.data.ActivityData
import com.ljw.initapp.data.ApplicationClass
import com.ljw.initapp.databinding.ActivityMainBinding
import com.ljw.initapp.util.BaseActivity
import com.ljw.initapp.util.DefaultUtil

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var applicationClass: ApplicationClass

    private val drawerLayout: DrawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //TODO progress 이미지는 drawable > loading.xml에서 바꿀 수 있음.
//        progressON()

        setContentView(binding.root)
        initLayout()
    }

    override fun onDestroy() {
        progressOFF()
        super.onDestroy()
    }

    private fun initLayout() {
        applicationClass = application as ApplicationClass
        DefaultUtil.init()
        DefaultUtil.getInstance().initStatusbar(window.decorView)
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    override fun onBackPressed() {
        moveActivity()
    }

    /**
     *  스택에 쌓여있는 activity가 있을 경우 -> 이전 액티비티로 이동
     *                         없을 경우 -> 앱 종료
     */
    private fun moveActivity() {
        val previousActivity = if (applicationClass.activityDataStack.isEmpty()) {
            null
        } else {
            applicationClass.activityDataStack.pop().activity
        }

        if (previousActivity != null) {
            val intent = Intent(this, previousActivity.javaClass)
            startActivity(intent)
            finish()
        } else {
            finish()
        }
    }

    /** 단순히 화면이동인지 스택에 쌓고 이동인지 구분 */
    private fun goToOtherActivity(activity: Class<*>, isStack: Boolean) {
        if (isStack) {
            val activityData = ActivityData(this@MainActivity)
            applicationClass.activityDataStack.push(activityData)
        }

        val intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("RtlHardcoded")
    fun onButtonClick(v: View) {
        when (v.id) {
            R.id.mainTotalLayout -> {
                if (currentFocus != null) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                }
            }

            R.id.mainMenuImageButton -> drawerLayout.openDrawer(Gravity.RIGHT)
            R.id.menuCloseImageButton -> if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT)
            }
        }
    }
}