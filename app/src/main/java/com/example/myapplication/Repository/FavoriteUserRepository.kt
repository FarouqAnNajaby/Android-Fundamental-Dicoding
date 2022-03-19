package com.example.myapplication.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.database.FavoriteDao
import com.example.myapplication.database.UserFavorite
import com.example.myapplication.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {

    private val mfavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mfavoriteDao = db.favoriteDao()
    }

    fun getAllFavoriteUser(): LiveData<List<UserFavorite>> = mfavoriteDao.getAllFavorite()
    fun checkFavorite(id: String): LiveData<Boolean> = mfavoriteDao.isFavorite(id)
    fun insertUserFavorite(favoriteUser: UserFavorite){
        executorService.execute{mfavoriteDao.insert(favoriteUser)}
    }

}