package com.munyao.safeboda.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.munyao.safeboda.R
import com.munyao.safeboda.databinding.FragmentSearchBinding
import com.munyao.safeboda.presentation.viewmodels.MainViewModel
import com.safeboda.domain.Resource
import com.safeboda.domain.models.GithubUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentSearchBinding? = null
    private var searchedUsername = ""
    private var emptyViewSeen = true

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.searchUser(query)
                    searchView.onActionViewCollapsed()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        toggleEmptyView(emptyViewSeen)
        mainViewModel.getSearchedUserLive().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding?.progressBar?.show()
                }
                is Resource.Error -> {
                    binding?.progressBar?.hide()
                }
                is Resource.Success -> {
                    binding?.progressBar?.hide()
                    if (it.data != null) {
                        searchedUsername = it.data!!.userName
                        bindToUI(it.data!!)
                    } else {
                        emptyViewSeen = true
                        toggleEmptyView(emptyViewSeen, "Nothing to see here")
                    }
                }
            }
        }
        initiateClicklisteners()
    }

    private fun toggleEmptyView(show: Boolean, message: String? = null) {
        if (show) {
            binding?.contentView?.visibility = View.GONE
            binding?.emptyLayout?.root?.visibility = View.VISIBLE
            message?.let {
                binding?.emptyLayout?.txtEmptyState?.text = message
            }
        } else {
            binding?.emptyLayout?.root?.visibility = View.GONE
            binding?.contentView?.visibility = View.VISIBLE
        }
    }

    private fun initiateClicklisteners() {
        binding?.apply {
            cardFollowers.setOnClickListener {
                if (searchedUsername.isNotEmpty()) {
                    val navDirection =
                        SearchFragmentDirections.actionSearchFragmentToFollowFragment(
                            searchedUsername,
                            true
                        )
                    findNavController().navigate(navDirection)
                }
            }
            cardFollowing.setOnClickListener {
                if (searchedUsername.isNotEmpty()) {
                    val navDirection =
                        SearchFragmentDirections.actionSearchFragmentToFollowFragment(
                            searchedUsername,
                            false
                        )
                    findNavController().navigate(navDirection)
                }
            }
        }
    }

    private fun bindToUI(githubUser: GithubUser) {
        binding?.apply {
            if (emptyViewSeen) {
                emptyLayout.root.visibility = View.GONE
                contentView.visibility = View.VISIBLE
                emptyViewSeen = false
            }
            setTextViewVisibility(txtEmail, githubUser.email)
            setTextViewVisibility(txtLocation, githubUser.location)
            setTextViewVisibility(txtOrganization, githubUser.company)
            setTextViewVisibility(txtTwitter, githubUser.twitterUsername)
            setTextViewVisibility(txtBlog, githubUser.blog)
            txtName.text = githubUser.name
            txtUserName.text = githubUser.userName
            txtFollowersCount.text = githubUser.followers.toString().trim()
            txtFollowingCount.text = githubUser.following.toString().trim()
            txtBio.text = githubUser.bio.trim()
            if (githubUser.createdAt.isNotEmpty()) {
                txtJoined.text = githubUser.createdAt
            }

            Glide
                .with(requireContext())
                .load(githubUser.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle_24)
                .error(R.drawable.ic_account_circle_24)
                .into(imgAvatar)
        }
    }

    private fun setTextViewVisibility(textView: TextView, text: String) {
        if (text.isNotEmpty()) {
            textView.visibility = View.VISIBLE
            textView.text = text
        } else {
            textView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}