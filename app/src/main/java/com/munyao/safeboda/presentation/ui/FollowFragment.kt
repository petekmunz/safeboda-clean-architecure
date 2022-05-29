package com.munyao.safeboda.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.munyao.safeboda.R
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
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = followAdapter
        }
        if (username.isNotEmpty()) {
            handleUILoadingStates()
            if (searchFollowers) {
                (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.label_followers)
                getFollowers()
            } else {
                (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.label_following)
                getFollowing()
            }
        } else {
            toggleEmptyView(true, getString(R.string.label_nothing_to_see))
        }
    }

    private fun toggleEmptyView(show: Boolean, message: String? = null) {
        if (show) {
            binding?.recyclerView?.visibility = View.GONE
            binding?.emptyLayout?.root?.visibility = View.VISIBLE
            message?.let {
                binding?.emptyLayout?.txtEmptyState?.text = message
            }
        } else {
            binding?.emptyLayout?.root?.visibility = View.GONE
            binding?.recyclerView?.visibility = View.VISIBLE
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

    private fun handleUILoadingStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            followAdapter.loadStateFlow.collectLatest {
                when (it.source.refresh) {
                    is LoadState.NotLoading -> {
                        binding?.progressBar?.hide()
                        if (followAdapter.itemCount > 0) {
                            if (binding?.emptyLayout?.root?.visibility == View.VISIBLE) {
                                toggleEmptyView(false, null)
                            }
                        } else {
                            toggleEmptyView(true, getString(R.string.label_nothing_to_see))
                        }
                    }
                    is LoadState.Loading -> {
                        if (followAdapter.itemCount < 1) {
                            binding?.progressBar?.show()
                        }
                    }
                    is LoadState.Error -> {
                        binding?.progressBar?.hide()
                        if (followAdapter.itemCount < 1) {
                            toggleEmptyView(true, getString(R.string.error_occurred_label))
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}