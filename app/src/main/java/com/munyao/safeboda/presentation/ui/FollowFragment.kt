package com.munyao.safeboda.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.munyao.safeboda.R
import com.munyao.safeboda.databinding.FragmentFollowBinding
import com.munyao.safeboda.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment() {
    private val args: FollowFragmentArgs by navArgs()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentFollowBinding? = null
    private var username: String = ""
    private var searchFollowers: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = args.username
        searchFollowers = args.isFollowersSearch
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (username.isNotEmpty()) {
            if (searchFollowers) {
                //Get followers
            } else {
                //Get following
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}