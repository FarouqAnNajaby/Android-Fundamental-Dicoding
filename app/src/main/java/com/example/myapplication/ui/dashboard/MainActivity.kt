package com.example.myapplication.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.Users
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.helper.createSearchViewMenu
import com.example.myapplication.ui.dashboard.adapter.UserListAdapter
import com.example.myapplication.ui.dashboard.detil.DetilActivity

class MainActivity : AppCompatActivity(), UserListAdapter.OnUserClickCallback {

    private lateinit var binding : ActivityMainBinding
    private lateinit var userAdapter: UserListAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.app_name)
        connectViewModel()
        getSearch()
        setAdapter()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateListData(list: List<Users>) {
        userAdapter.listUser.clear()
        userAdapter.listUser.addAll(list)
        userAdapter.notifyDataSetChanged()
        if (list.isEmpty()) {
            binding.rvUser.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
            binding.imageEmpty.visibility = View.VISIBLE
        } else {
            binding.tvNoData.visibility = View.GONE
            binding.imageEmpty.visibility = View.GONE
        }
    }

    override fun onUserClicked(data: Users) {
        DetilActivity.start(this, data.login)
    }

    private fun getSearch(){
        mainViewModel.getSearchUser().observe(this) {
            if (it != null) {
                updateListData(it)
                showLoading(false)
            }
        }
    }

    private fun connectViewModel(){
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvUser.visibility = View.GONE
            binding.welcomeText.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.welcomeText.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    private fun searchUser(query: String) {
        showLoading(true)
        mainViewModel.searchUser(query)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        createSearchViewMenu(menu) {
            searchUser(it)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun setAdapter(){
        userAdapter = UserListAdapter(this, mutableListOf(), this)
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }
        binding.welcomeText.visibility = View.VISIBLE
    }
}