package com.dispy.showgithuballuser

class GetAllUserCommand : BaseConnenct() {

    init {
        //setParams here
    }

    override fun getPreferredUrl(): String {
        return "https://api.github.com/users"
    }

}
