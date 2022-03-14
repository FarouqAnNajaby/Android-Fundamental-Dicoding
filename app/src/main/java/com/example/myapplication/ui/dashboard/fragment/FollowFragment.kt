package com.example.myapplication.ui.dashboard.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.Users
import com.example.myapplication.databinding.FragmentFollowBinding
import com.example.myapplication.ui.dashboard.MainViewModel
import com.example.myapplication.ui.dashboard.adapter.UserListAdapter
import com.example.myapplication.ui.dashboard.detil.DetilActivity

class FollowFragment : Fragment(), UserListAdapter.OnUserClickCallback  {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userAdapter: UserListAdapter
    private var type: String? = null
    private var username: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putString("type", param1)
                    putString("username", param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type", "")
            username = it.getString("username", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        mainViewModel.getFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                updateList(it)
                showLoading(false)
            }
        }

        mainViewModel.getFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                updateList(it)
                showLoading(false)
            }
        }

        userAdapter = UserListAdapter(requireContext(), mutableListOf(), this)
        binding.rvFollow.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }

        showLoading(true)
        getFollowList(username.orEmpty(), type.orEmpty())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateList(list: List<Users>) {
        userAdapter.listUser.clear()
        userAdapter.listUser.addAll(list)
        userAdapter.notifyDataSetChanged()
        if (list.isEmpty()) {
            binding.rvFollow.visibility = View.GONE
            binding.tvNoDataFollow.visibility = View.VISIBLE
        } else {
            binding.tvNoDataFollow.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvFollow.visibility = View.GONE
            binding.progressBarFollow.visibility = View.VISIBLE
            binding.tvNoDataFollow.visibility = View.GONE
        } else {
            binding.rvFollow.visibility = View.VISIBLE
            binding.progressBarFollow.visibility = View.GONE
        }
    }

    private fun getFollowList(username: String, type: String) {
        if (type == "Pengikut") mainViewModel.listFollowers(username)
        else mainViewModel.listFollowing(username)
    }

    override fun onUserClicked(data: Users) {
        DetilActivity.start(requireContext(), data.login)
    }
}