package com.munyao.safeboda.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.munyao.safeboda.databinding.FragmentSearchBinding
import com.munyao.safeboda.presentation.viewmodels.MainViewModel
import com.safeboda.domain.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentSearchBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        mainViewModel.getSearchedUserLive().observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    //Show loading
                }
                is Resource.Error -> {
                    //Show error
                }
                is Resource.Success -> {
                    //Show content
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}