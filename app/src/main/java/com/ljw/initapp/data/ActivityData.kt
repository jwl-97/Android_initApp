package com.ljw.initapp.data

import android.app.Activity

class ActivityData {
    var activity: Activity? = null
    var requestID: String? = null
    var state = 0

    constructor() {}
    constructor(activity: Activity?) {
        this.activity = activity
        requestID = null
        state = -1
    }
}