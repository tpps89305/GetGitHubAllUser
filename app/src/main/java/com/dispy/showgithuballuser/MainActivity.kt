package com.dispy.showgithuballuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dispy.showgithuballuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userAdapter: UserAdapter = UserAdapter(this, ArrayList<User>())
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
        userViewModel.getUsers().observe(this, object : Observer<List<User?>?> {
            override fun onChanged(t: List<User?>?) {
                userAdapter.swapItems(t as List<User>)
            }
        })
    }
}