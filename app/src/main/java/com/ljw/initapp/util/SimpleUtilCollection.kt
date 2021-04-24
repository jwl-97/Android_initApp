package com.ljw.initapp.util

import android.os.Build
import android.os.Handler
import android.os.Message
import android.text.Html
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.ljw.initapp.util.DLog
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class SimpleUtilCollection {
    companion object {
        /**
         * init
         */

        fun htmlToString(string: String): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                Html.fromHtml(string).toString()
            }
        }

        const val ERROR = 99

        fun setErrorMessage(text: String?, errCode: Int?, handler: Handler) {
            val message = Message.obtain()
            message.what = ERROR
            if (message != null) message.obj = text
            if (errCode != null) message.arg1 = errCode
            handler.sendMessage(message)
        }

        fun makeSlackText(tell_no: String, text: String?): String {
            return "[ tell_no : $tell_no ] $text"
        }

        fun sendToSlack(url: String, text: String) {
            val json = JSONObject()
            json.put("text", text)

            val client = OkHttpClient()
            val reqBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json.toString()
            )

            val request: Request = Request.Builder()
                .url(url)
                .post(reqBody)
                .build()
            client.newCall(request).enqueue(sendToSlackCallback)
        }

        private val sendToSlackCallback: Callback = object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                DLog.d("SLACK", "ERROR Message : " + e.message)
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val responseData: String = response.toString()
                DLog.d("SLACK", "responseData : $responseData")
            }
        }

        fun String.toJson(): String {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val jp = JsonParser()
            val je: JsonElement = jp.parse(this)
            return gson.toJson(je)
        }
    }
}
