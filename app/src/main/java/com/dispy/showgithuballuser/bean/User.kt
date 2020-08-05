package com.dispy.showgithuballuser.bean

class User(
    val login: String,
    val avatarUrl: String,
    val name: String = "",
    val location: String = "",
    val url: String = ""
)