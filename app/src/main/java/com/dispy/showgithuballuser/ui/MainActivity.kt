package com.dispy.showgithuballuser.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dispy.showgithuballuser.bean.User
import com.dispy.showgithuballuser.databinding.ActivityMainBinding
import com.dispy.showgithuballuser.function.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userAdapter: UserAdapter =
        UserAdapter(this, ArrayList())
    private lateinit var userViewModel: UserViewModel

    private var totalItemsCount: Int = 0
    private var isLoading: Boolean = false
    private var userIdLastSeen: Int = 0
    private var currentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = userAdapter

        userViewModel = UserViewModel()
        binding.viewModel = userViewModel
        isLoading = true
        userViewModel.getData(0)
        userViewModel.getUsers().observe(this,
            Observer<List<User?>?> { t ->
                val data: List<User> = t as List<User>
                userIdLastSeen = data.last().id
                totalItemsCount += data.size
                userAdapter.swapItems(data)
                isLoading = false
            }
        )
        userAdapter.setOnClickListener(
            object : UserAdapter.OnClickListener {
                override fun onItemClick(login: String) {
                    val intent = Intent(applicationContext, PaginationActivity::class.java)
                    intent.putExtra("login", login)
                    startActivity(intent)
                }
            }
        )
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastItemsPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (totalItemsCount <= lastItemsPosition + 1) {
                    if (!isLoading && currentPage < 20) {
                        Log.i("MainActivity", "Download more data, last user ID = $userIdLastSeen, page $currentPage")
                        isLoading = true
                        userViewModel.getData(userIdLastSeen)
                        currentPage++
                    }
                    else if (currentPage >= 20) {
                        Log.i("MainActivity", "Page amount limit is 20, don't load more data.")
                    }
                }
            }
        })
    }
}