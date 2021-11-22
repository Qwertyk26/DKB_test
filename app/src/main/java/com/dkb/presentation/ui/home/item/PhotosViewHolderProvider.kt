package com.dkb.presentation.ui.home.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dkb.databinding.ItemPhotoBinding
import com.dkb.domain.entity.response.PhotosItem
import com.dkb.presentation.ui.base.adapter.BaseViewHolder
import com.dkb.presentation.ui.base.adapter.BaseViewHolderProvider
import com.squareup.picasso.Picasso

class PhotosViewHolderProvider: BaseViewHolderProvider<PhotosItem>() {

    private lateinit var binding: ItemPhotoBinding
    private var onItemClick: (PhotosItem) -> Unit = {}

    fun createViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        onItemClick:(PhotosItem) -> Unit
    ): BaseViewHolder<PhotosItem> {
        this.onItemClick = onItemClick
        return createViewHolder(parent, inflater)
    }

    override fun createViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): BaseViewHolder<PhotosItem> {
        binding = ItemPhotoBinding.inflate(inflater, parent, false)
        return BaseViewHolder(binding.root, ::bind)
    }

    private fun bind(item: PhotosItem) {
        binding.title = item.title
        Picasso.get().load(item.thumbnailUrl).into(binding.photoIv)
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
    }
}