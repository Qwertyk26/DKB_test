package com.dkb.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import com.dkb.R
import com.dkb.databinding.FragmentHomeBinding
import com.dkb.domain.entity.response.PhotosItem
import com.dkb.presentation.ui.base.BaseFragment
import com.dkb.presentation.ui.base.adapter.BaseRecyclerViewAdapter
import com.dkb.presentation.ui.base.adapter.BaseRecyclerViewAdapterWrapper
import com.dkb.presentation.ui.home.item.PhotosViewHolderProvider
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment() {

    private val viewModel by viewModel<HomeViewModel>()
    private val adapter: BaseRecyclerViewAdapter<PhotosItem> by lazy { getAdapter() }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getImages().observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty().not()) {
                progress_bar.visibility = View.GONE
                adapter.addList(it)
            }
        })
    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = GridLayoutManager(requireContext(),2)
        recycler_view.adapter = adapter
    }

    private fun getAdapter() = BaseRecyclerViewAdapterWrapper { parent, _ ->
        get<PhotosViewHolderProvider>().createViewHolder(parent,
            layoutInflater, onItemClick = { photoItem ->
                navController.navigate(R.id.action_to_detail, bundleOf("photoItem" to photoItem))
            })
    }
}