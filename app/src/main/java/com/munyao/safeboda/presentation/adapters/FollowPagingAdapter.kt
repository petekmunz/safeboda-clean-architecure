package com.munyao.safeboda.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.munyao.safeboda.R
import com.munyao.safeboda.databinding.ItemFollowerOrFollowingBinding
import com.safeboda.domain.models.FollowerOrFollowingModel

class FollowPagingAdapter :
    PagingDataAdapter<FollowerOrFollowingModel, FollowPagingAdapter.FollowViewHolder>(
        DIFF_UTIL
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFollowerOrFollowingBinding.inflate(
            inflater,
            parent,
            false
        )
        return FollowViewHolder(binding, binding.root.context)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FollowViewHolder(
        private val binding: ItemFollowerOrFollowingBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(followerOrFollowingModel: FollowerOrFollowingModel?) {
            followerOrFollowingModel?.let {
                binding.txtUserName.text = it.username

                Glide
                    .with(context)
                    .load(it.avatarUrl)
                    .circleCrop()
                    .placeholder(R.drawable.ic_account_circle_24)
                    .error(R.drawable.ic_account_circle_24)
                    .into(binding.imgAvatar)
            }
        }
    }

    companion object {
        private val DIFF_UTIL =
            object : DiffUtil.ItemCallback<FollowerOrFollowingModel>() {
                override fun areItemsTheSame(
                    oldItem: FollowerOrFollowingModel,
                    newItem: FollowerOrFollowingModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: FollowerOrFollowingModel,
                    newItem: FollowerOrFollowingModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}