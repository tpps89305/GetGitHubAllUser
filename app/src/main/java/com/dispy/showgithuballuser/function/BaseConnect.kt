package com.dispy.showgithuballuser.function

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.collections.ArrayList

abstract class BaseConnect {
    private var mListener: CallBackListener? = null
    private val postParams: MutableList<NameValuePair> =
        ArrayList()

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val response = msg.obj.toString()
            if (TextUtils.isEmpty(response)) {
                mListener!!.onNoResult()
            } else {
                mListener!!.onCallBack(response)
            }
        }
    }

    private fun executePostRequest(
        urlStr: String,
        params: List<NameValuePair>
    ): String {
        Log.i("BaseConnect", "Connect to: $urlStr")
        try {
            val url = URL(urlStr)
            val conn =
                url.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.requestMethod = "GET"
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json")
            if (params.isNotEmpty()) {
                val paramsStrBuild = StringBuilder()
                for (ii in params.indices) {
                    val pair = params[ii]
                    paramsStrBuild.append(URLEncoder.encode(pair.name, "UTF-8"))
                    paramsStrBuild.append("=")
                    paramsStrBuild.append(URLEncoder.encode(pair.value, "UTF-8"))
                    if (ii != params.size - 1) {
                        paramsStrBuild.append("&")
                    }
                }
                val paramsStr = paramsStrBuild.toString()
                val os = conn.outputStream
                os.write(paramsStr.toByteArray())
                os.flush()
            }
            if (conn.responseCode != HttpURLConnection.HTTP_OK) {
                throw RuntimeException(
                    "Failed : HTTP error code : "
                            + conn.responseCode
                )
            }
            val br = BufferedReader(
                InputStreamReader(
                    conn.inputStream
                )
            )
            val outputBuild = StringBuilder()
            var output: String?
            while (br.readLine().also { output = it } != null) {
                outputBuild.append(output)
            }
            conn.disconnect()
            return outputBuild.toString()
        } catch (e: IOException) {
            Log.e("BaseConnect", "Fail to download data!", e)
        }
        return ""
    }

    fun getResponse(listener: CallBackListener) {
        mListener = listener
        Thread(Runnable {
            val response: String = executePostRequest(
                getPreferredUrl() + getDomainSuffix(),
                postParams
            )
            val msg = Message()
            msg.obj = response
            handler.sendMessage(msg)
        }).start()
    }

    abstract fun getPreferredUrl(): String

    abstract fun getDomainSuffix(): String

    interface CallBackListener {
        fun onCallBack(response: String?)
        fun onNoResult()
    }
}