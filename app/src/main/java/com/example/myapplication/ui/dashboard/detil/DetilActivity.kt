package com.example.myapplication.ui.dashboard.detil

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.data.UserDetail
import com.example.myapplication.database.UserFavorite
import com.example.myapplication.databinding.ActivityDetilBinding
import com.example.myapplication.helper.Constanta
import com.example.myapplication.ui.dashboard.MainViewModel
import com.example.myapplication.ui.dashboard.adapter.PagerAdapter
import com.example.myapplication.ui.dashboard.fragment.FollowFragment
import com.example.myapplication.ui.favorite.FavoriteViewModelFactory
import com.example.myapplication.ui.favorite.FavoriteViewModelFactory.Companion.getInstance
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class DetilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetilBinding
    private lateinit var mainViewModel: MainViewModel
    private var username: String? = null
    private var favUser: UserFavorite? = null
    private lateinit var favUserViewModel: FavoriteUserViewModel
    private var userId: String? = null
    private var usernameFav: String? = null
    private var avatar: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent?.getStringExtra(Constanta.USERNAME_KEY)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        connectViewModel()

        mainViewModel.getDetailUser().observe(this) {
            if (it != null) {
                updateUserData(it)
                showLoading(false)
            }
        }

        username?.let {
            showLoading(true)
            mainViewModel.detailUser(it)
        }

        initViewPager()

        favUserViewModel = obtainViewModel(this@DetilActivity)

        binding.ivFavorite.setOnClickListener {
            if (binding.ivFavorite.tag.toString() == "unlike") {
                binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
                favUserViewModel.insert(favUser as UserFavorite)
                binding.ivFavorite.tag = "like"
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                favUserViewModel.delete(favUser as UserFavorite)
                binding.ivFavorite.tag = "unlike"
            }
        }
    }
    private fun checkFav(userId: String) {
        binding.ivFavorite.visibility = View.VISIBLE
        favUserViewModel.checkFavorite(userId).observe(this) { check ->
            if (check) {
                binding.ivFavorite.tag = "like"
                binding.ivFavorite.imageTintList = ColorStateList.valueOf(Color.RED)
            } else {
                binding.ivFavorite.tag = "unlike"
                binding.ivFavorite.imageTintList = ColorStateList.valueOf(Color.BLACK)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    private fun connectViewModel(){
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
    }

    private fun updateUserData(data: UserDetail) {
        Glide.with(this)
            .load(data.avatar_url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.noimg)
            )
            .into(binding.ivUser)

        binding.tvUsername.text = data.login

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                appBarLayout, verticalOffset ->

            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0){
                if (data.name == null) {
                    binding.toolbarLayout.title = resources.getString(R.string.no_name)
                } else {
                    binding.toolbarLayout.title = data.name
                }
            } else{
                binding.toolbarLayout.title = ""
            }
        })

        binding.tvRepository.text =
            resources.getQuantityString(
                R.plurals.numberOfRepo,
                data.public_repos,
                data.public_repos
            )

        if (data.name == null) {
            binding.tvName.text = resources.getString(R.string.no_name)
        } else {
            binding.tvName.text = data.name
        }

        if (data.location == null) {
            binding.tvLocation.text = resources.getString(R.string.no_location)
        } else {
            binding.tvLocation.text = data.location
        }

        if (data.company == null) {
            binding.tvCompany.text = resources.getString(R.string.no_company)
        } else {
            binding.tvCompany.text = data.company
        }

        favUser = UserFavorite(data.id.toString(), data.login, data.avatar_url)

        binding.tvFollow.text =
            resources.getString(R.string.no_follow, data.following, data.followers)
        checkFav(data.id.toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initViewPager() {
        val pages = listOf(
            FollowFragment.newInstance("Mengikuti", username.orEmpty()),
            FollowFragment.newInstance("Pengikut", username.orEmpty())
        )

        val titles = listOf(
            resources.getString(R.string.following), resources.getString(R.string.followers)
        )

        binding.included.vpFollow.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.included.vpFollow.adapter = PagerAdapter(pages, supportFragmentManager, lifecycle)
        binding.included.vpFollow.offscreenPageLimit = 2
        binding.included.vpFollow.isUserInputEnabled = true

        TabLayoutMediator(binding.included.tabLayout, binding.included.vpFollow) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    companion object {
        fun start(context: Context, username: String) {
            Intent(context, DetilActivity::class.java).apply {
                this.putExtra(Constanta.USERNAME_KEY, username)
                context.startActivity(this)
            }
        }
    }
}