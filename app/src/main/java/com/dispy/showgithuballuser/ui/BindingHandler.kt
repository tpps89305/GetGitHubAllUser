package com.dispy.showgithuballuser.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingHandler {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun bindImage(imageView: ImageView, url: String?) {
        val context = imageView.context
        Glide.with(context)
            .load(url)
            .into(imageView)
    }
}