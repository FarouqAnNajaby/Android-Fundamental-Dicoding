package com.example.myapplication.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.database.UserFavorite

class FavDiffCallback(
    private val mOldFavList: List<UserFavorite>,
    private val mNewFavList: List<UserFavorite>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavList[oldItemPosition]
        val newEmployee = mNewFavList[newItemPosition]
        return oldEmployee.id == newEmployee.id && oldEmployee.username == newEmployee.username
    }

}