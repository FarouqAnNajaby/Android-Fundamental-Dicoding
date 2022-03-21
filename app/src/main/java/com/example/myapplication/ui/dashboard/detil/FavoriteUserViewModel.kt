package com.example.myapplication.ui.dashboard.detil

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.UserFavorite
import com.example.myapplication.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun insert(userFavorite: UserFavorite) {
        mFavoriteUserRepository.insertUserFavorite(userFavorite)
    }

    fun delete(userFavorite: UserFavorite) {
        mFavoriteUserRepository.deleteUserFavorite(userFavorite)
    }

    fun checkFavorite(id: String): LiveData<Boolean> =
        mFavoriteUserRepository.checkFavorite(id)
}