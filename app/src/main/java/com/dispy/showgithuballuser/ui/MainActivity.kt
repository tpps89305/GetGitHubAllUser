package com.dispy.showgithuballuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dispy.showgithuballuser.bean.User
import com.dispy.showgithuballuser.databinding.ActivityMainBinding
import com.dispy.showgithuballuser.function.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userAdapter: UserAdapter =
        UserAdapter(this, ArrayList())
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.adapter = userAdapter

        userViewModel = UserViewModel()
        binding.viewModel = userViewModel
        userViewModel.getAllUsers()
        userViewModel.getUsers().observe(this,
            Observer<List<User?>?> { t -> userAdapter.swapItems(t as List<User>) })
    }
}