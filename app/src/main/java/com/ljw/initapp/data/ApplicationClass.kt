package com.ljw.initapp.data

import android.app.Application
import com.ljw.initapp.util.Stack
import com.zoyi.channel.plugin.android.ChannelIO

class ApplicationClass : Application() {
    var activityDataStack: Stack<ActivityData> = Stack()
    var apiToken: String? = null

    override fun onCreate() {
        super.onCreate()

        ChannelIO.initialize(this)
    }

    fun newActivityDataStack() {
        activityDataStack = Stack()
    }
}