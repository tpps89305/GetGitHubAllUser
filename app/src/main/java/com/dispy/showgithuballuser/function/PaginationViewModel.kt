package com.dispy.showgithuballuser.function

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dispy.showgithuballuser.bean.User
import org.json.JSONObject

class PaginationViewModel {

    private val user: MutableLiveData<User> = MutableLiveData()

    fun getUser(): LiveData<User> {
        return user
    }

    fun getData(username: String) {
        GetAllUserCommand(username).getResponse(object : BaseConnect.CallBackListener {
            override fun onCallBack(response: String?) {
                val jsonObject = JSONObject(response)
                val login = jsonObject.optString("login")
                val avatarUrl = jsonObject.optString("avatar_url")
                val name = jsonObject.optString("name")
                val location = jsonObject.optString("location")
                val url = jsonObject.optString("url")
                val id = jsonObject.optInt("id")
                val user = User(id, login, avatarUrl, name, location, url)
                this@PaginationViewModel.user.value = user
            }

            override fun onNoResult() {
                Log.w("GetAllUserCommand", "May not get GitHub user!")
            }

        })
    }

}