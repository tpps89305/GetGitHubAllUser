package com.dispy.showgithuballuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONArray
import org.json.JSONObject

class UserViewModel {

    private val mUsers: MutableLiveData<List<User>> = MutableLiveData()

    fun getUsers(): LiveData<List<User>> {
        return mUsers
    }

    fun getAllUsers() {
        GetAllUserCommand().getResponse(object : BaseConnenct.CallBackListener {
            override fun onCallBack(response: String?) {
                Log.d("GetAllUserCommand", "Response = " + response)
                val jsonArray: JSONArray = JSONArray(response)
                var users = ArrayList<User>()
                for (x in 0..jsonArray.length() - 1) {
                    val jsonObject: JSONObject = jsonArray.getJSONObject(x)
                    users.add(User(jsonObject.getString("login"), jsonObject.getString("avatar_url")))
                }
                mUsers.value = users
            }

            override fun onNoResult() {
                Log.w("GetAllUserCommand", "May not get GitHub user!")
            }

        })
    }
}