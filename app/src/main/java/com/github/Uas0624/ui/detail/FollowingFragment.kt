package com.github.Uas0624.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.Uas0624.R
import com.github.Uas0624.databinding.FragmentFollowBinding
import com.github.Uas0624.ui.main.MainAdapter

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.USERNAME).toString()

        _binding = FragmentFollowBinding.bind(view)

        adapter = MainAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rcMain.setHasFixedSize(true)
            rcMain.layoutManager = LinearLayoutManager(activity)
            rcMain.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)

        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null)
                adapter.setList(it)
            showLoading(false)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}