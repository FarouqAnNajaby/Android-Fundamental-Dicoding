package com.example.myapplication.ui.dashboard

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.BuildConfig
import com.example.myapplication.api.ApiInterface
import com.example.myapplication.api.ApiService
import com.example.myapplication.data.SearchResponse
import com.example.myapplication.data.UserDetail
import com.example.myapplication.data.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listSearch = MutableLiveData<List<Users>>()
    private val userDetail = MutableLiveData<UserDetail>()
    private val following = MutableLiveData<List<Users>>()
    private val followers = MutableLiveData<List<Users>>()

    private val token = BuildConfig.GITHUB_TOKEN;
    private val request = ApiService.buildService(ApiInterface::class.java)

    fun getSearchUser(): LiveData<List<Users>> {
        return listSearch
    }

    fun getDetailUser(): LiveData<UserDetail> {
        return userDetail
    }

    fun getFollowers(): LiveData<List<Users>> {
        return followers
    }

    fun getFollowing(): LiveData<List<Users>> {
        return following
    }

    fun searchUser(username: String) {
        val call = request.searchUser(username, token)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listSearch.postValue(it.list_user)
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }

    fun detailUser(username: String) {
        val call = request.getDetail(username, token)

        call.enqueue(object : Callback<UserDetail> {
            override fun onResponse(
                call: Call<UserDetail>,
                response: Response<UserDetail>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        userDetail.postValue(it)
                        Log.i(TAG, "CEK: $userDetail")
                    }
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }

    fun listFollowers(username: String) {
        val call = request.getFollowers(username, token)

        call.enqueue(object : Callback<List<Users>> {
            override fun onResponse(
                call: Call<List<Users>>,
                response: Response<List<Users>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        followers.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }

    fun listFollowing(username: String) {
        val call = request.getFollowings(username, token)

        call.enqueue(object : Callback<List<Users>> {
            override fun onResponse(
                call: Call<List<Users>>,
                response: Response<List<Users>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        following.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }
}