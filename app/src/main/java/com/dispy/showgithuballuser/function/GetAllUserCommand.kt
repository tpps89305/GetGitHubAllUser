package com.dispy.showgithuballuser.function

import android.text.TextUtils
import java.lang.StringBuilder

class GetAllUserCommand(username: String = "", since: Int = 0) : BaseConnect() {

    private var paramsBuilder: StringBuilder = StringBuilder()

    init {
        //Get the information of a user
        if (!TextUtils.isEmpty(username))
            paramsBuilder.append("/").append(username)
        //Get all user to list, per page.
        if (since >= 0)
            paramsBuilder.append("?").append("since=$since")
    }

    override fun getPreferredUrl(): String {
        return "https://api.github.com/users"
    }

    override fun getDomainSuffix(): String {
        return paramsBuilder.toString()
    }

}
