package com.dispy.showgithuballuser.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dispy.showgithuballuser.bean.User
import com.dispy.showgithuballuser.databinding.ItemUserBinding


class UserAdapter(private val context: Context, private val users: ArrayList<User>): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private lateinit var listener: OnClickListener

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return UserViewHolder(
            ItemUserBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            val user: User = users[position]
            holder.bind(user)
            holder.itemView.setOnClickListener {
                listener.onItemClick(user.login)
            }
        }
    }

    fun swapItems(newItems: List<User>) {
        users.addAll(newItems)
        notifyDataSetChanged()
    }

    internal class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User?) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    interface OnClickListener {
        fun onItemClick(login: String)
    }
}
