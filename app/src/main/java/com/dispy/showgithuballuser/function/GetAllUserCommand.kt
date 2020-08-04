package com.dispy.showgithuballuser.function

class GetAllUserCommand : BaseConnect() {

    init {
        //setParams here
    }

    override fun getPreferredUrl(): String {
        return "https://api.github.com/users"
    }

}
