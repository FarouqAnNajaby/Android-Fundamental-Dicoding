package com.example.myapplication.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFavoriteActiviyBinding
import com.example.myapplication.ui.dashboard.detil.DetilActivity
import com.example.myapplication.ui.dashboard.detil.FavoriteUserViewModel
import com.example.myapplication.ui.favorite.adapter.AdapterFavoriteUser

class FavoriteActiviy : AppCompatActivity(), AdapterFavoriteUser.OnFavoriteClickCallback {

    private var binding: ActivityFavoriteActiviyBinding? = null
    private lateinit var adapter: AdapterFavoriteUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteActiviyBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = resources.getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val favViewModel = obtainViewModel(this)
        favViewModel.getAllListFavorite().observe(this) { favList ->
            if (favList != null) {
                adapter.setListFavorite(favList)
            }
        }
        adapter = AdapterFavoriteUser(this)
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteGetDataViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteGetDataViewModel::class.java]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFavoriteClicked(username: String?) {
        if (username != null) {
            DetilActivity.start(this, username)
        }
    }
}