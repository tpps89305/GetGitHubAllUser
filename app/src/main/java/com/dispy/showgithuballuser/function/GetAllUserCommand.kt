package com.dispy.showgithuballuser.function

import android.text.TextUtils
import java.lang.StringBuilder

class GetAllUserCommand(val username: String) : BaseConnect() {

    private var paramsBuilder: StringBuilder = StringBuilder()

    init {
        //setParams here
        if (!TextUtils.isEmpty(username))
            paramsBuilder.append("/").append(username)
    }

    override fun getPreferredUrl(): String {
        return "https://api.github.com/users"
    }

    override fun getDomainSuffix(): String {
        return paramsBuilder.toString()
    }

}
