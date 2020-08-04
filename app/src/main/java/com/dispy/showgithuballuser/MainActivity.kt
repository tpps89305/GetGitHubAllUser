package com.dispy.showgithuballuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dispy.showgithuballuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        GetAllUserCommand().getResponse(object : BaseConnenct.CallBackListener {
            override fun onCallBack(response: String?) {
                Log.d("GetAllUserCommand", "Response = " + response)
            }

            override fun onNoResult() {
                Log.w("GetAllUserCommand", "May not get GitHub user!")
            }

        })
    }
}