package com.dispy.showgithuballuser.function

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dispy.showgithuballuser.bean.User
import org.json.JSONArray
import org.json.JSONObject

class UserViewModel {

    private val mUsers: MutableLiveData<List<User>> = MutableLiveData()

    fun getUsers(): LiveData<List<User>> {
        return mUsers
    }

    fun getData(since: Int) {
        GetAllUserCommand(since = since).getResponse(object : BaseConnect.CallBackListener {
            override fun onCallBack(response: String?) {
                Log.d("GetAllUserCommand", "Response = $response")
                val jsonArray: JSONArray = JSONArray(response)
                val users = ArrayList<User>()
                for (x in 0 until jsonArray.length()) {
                    val jsonObject: JSONObject = jsonArray.getJSONObject(x)
                    val login = jsonObject.getString("login")
                    val avatarUrl = jsonObject.getString("avatar_url")
                    val id = jsonObject.getInt("id")
                    users.add(User(id, login, avatarUrl))
                }
                mUsers.value = users
            }

            override fun onNoResult() {
                Log.w("GetAllUserCommand", "May not get GitHub user!")
            }

        })
    }
}