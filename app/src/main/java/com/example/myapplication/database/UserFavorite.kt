package com.example.myapplication.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class UserFavorite (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "avatar")
    var avatar: String? = null

): Parcelable