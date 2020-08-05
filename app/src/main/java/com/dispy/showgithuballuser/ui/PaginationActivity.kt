package com.dispy.showgithuballuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dispy.showgithuballuser.databinding.ActivityPaginationBinding
import com.dispy.showgithuballuser.function.PaginationViewModel

class PaginationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaginationBinding
    private lateinit var viewModel: PaginationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaginationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent

        viewModel = PaginationViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getData(intent.getStringExtra("login")!!)
    }
}