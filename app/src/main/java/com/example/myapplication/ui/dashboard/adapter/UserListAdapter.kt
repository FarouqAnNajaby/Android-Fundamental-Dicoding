package com.example.myapplication.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.data.Users
import com.example.myapplication.databinding.ItemListUserBinding

class UserListAdapter(private val context: Context,
                      val listUser: MutableList<Users> = mutableListOf(),
                      val listener: OnUserClickCallback? = null)
    : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserListAdapter.ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemListUserBinding.bind(itemView)

        fun bind(dataUser: Users) {
            with(itemView) {
                binding.tvId.text = dataUser.id.toString()
                binding.tvUsername.text = dataUser.login
                Glide.with(this)
                    .load(dataUser.avatarUrl)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.noimg)
                    )
                    .into(binding.ivImage)

                setOnClickListener {
                    listener?.onUserClicked(dataUser)
                }
            }
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnUserClickCallback {
        fun onUserClicked(data: Users)
    }
}