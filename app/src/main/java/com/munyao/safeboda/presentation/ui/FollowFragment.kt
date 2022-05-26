package com.munyao.safeboda.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.munyao.safeboda.databinding.FragmentFollowBinding
import com.munyao.safeboda.presentation.adapters.FollowPagingAdapter
import com.munyao.safeboda.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowFragment : Fragment() {
    private val args: FollowFragmentArgs by navArgs()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentFollowBinding? = null
    private var username: String = ""
    private var searchFollowers: Boolean = true
    private val followAdapter = FollowPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = args.username
        searchFollowers = args.isFollowersSearch
        binding?.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = followAdapter
        }
        if (username.isNotEmpty()) {
            if (searchFollowers) {
                getFollowers()
            } else {
                getFollowing()
            }
        }
    }

    private fun getFollowers() {
        if (username.isNotEmpty()) {
            viewLifecycleOwner.lifecycleScope.launch {
                mainViewModel.getFollowers(username).collectLatest {
                    followAdapter.submitData(it)
                }
            }
        }
    }

    private fun getFollowing() {
        if (username.isNotEmpty()) {
            viewLifecycleOwner.lifecycleScope.launch {
                mainViewModel.getFollowing(username).collectLatest {
                    followAdapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}