package com.dkb.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dkb.R
import com.dkb.databinding.FragmentDetailBinding
import com.dkb.databinding.FragmentHomeBinding
import com.dkb.domain.entity.response.PhotosItem
import com.dkb.presentation.ui.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment: BaseFragment() {

    private lateinit var binding: FragmentDetailBinding
    private var photoItem: PhotosItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoItem = arguments?.getParcelable("photoItem")
        Log.d("photoItem", "${photoItem}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.title = photoItem?.title
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tool_bar.setNavigationOnClickListener {
            navController.popBackStack()
        }
        Picasso.get().load(photoItem?.url).into(binding.photoDetailIv)
    }
}