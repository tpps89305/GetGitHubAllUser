package com.dispy.showgithuballuser.function

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dispy.showgithuballuser.bean.User
import org.json.JSONArray
import org.json.JSONObject

class UserViewModel {

    private val users: MutableLiveData<List<User>> = MutableLiveData()

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    fun getData(since: Int) {
        GetAllUserCommand(since = since).getResponse(object : BaseConnect.CallBackListener {
            override fun onCallBack(response: String?) {
                Log.d("GetAllUserCommand", "Response = $response")
                val jsonArray = JSONArray(response)
                val users = ArrayList<User>()
                for (x in 0 until jsonArray.length()) {
                    val jsonObject: JSONObject = jsonArray.optJSONObject(x)
                    val login = jsonObject.optString("login")
                    val avatarUrl = jsonObject.optString("avatar_url")
                    val id = jsonObject.optInt("id")
                    users.add(User(id, login, avatarUrl))
                }
                this@UserViewModel.users.value = users
            }

            override fun onNoResult() {
                Log.w("GetAllUserCommand", "May not get GitHub user!")
            }

        })
    }
}