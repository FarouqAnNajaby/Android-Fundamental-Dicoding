package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userFavorite: UserFavorite)

    @Delete
    fun delete(userFavorite: UserFavorite)

    @Query("SELECT * from userfavorite")
    fun getAllFavorite(): LiveData<List<UserFavorite>>

    @Query("SELECT EXISTS(SELECT * from userfavorite WHERE id = :id)")
    fun isFavorite(id: String): LiveData<Boolean>

}