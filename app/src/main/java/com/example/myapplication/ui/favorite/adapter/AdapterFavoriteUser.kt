package com.example.myapplication.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.database.UserFavorite
import com.example.myapplication.databinding.ItemListUserBinding
import com.example.myapplication.helper.FavDiffCallback

class AdapterFavoriteUser
    (private val listener: OnFavoriteClickCallback? = null)
    : RecyclerView.Adapter<AdapterFavoriteUser.ViewHolder>() {

    private val listFavorite = ArrayList<UserFavorite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
        ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterFavoriteUser.ViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int =listFavorite.size

    inner class ViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: UserFavorite) {
            with(binding) {
                tvId.text = favorite.id
                tvUsername.text = favorite.username
                Glide.with(itemView)
                    .load(favorite.avatar)
                    .apply(
                        RequestOptions().placeholder(R.drawable.noimg)
                    )
                    .into(ivImage)
                itemView.setOnClickListener {
                    listener?.onFavoriteClicked(favorite.username)
                }
            }
        }
    }

    interface OnFavoriteClickCallback {
        fun onFavoriteClicked(username: String?)
    }

    fun setListFavorite(listFavorite: List<UserFavorite>) {
        val diffCallback = FavDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }
}