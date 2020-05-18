package com.mohamed.halim.essa.backgroundchanger.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamed.halim.essa.backgroundchanger.R
import com.mohamed.halim.essa.backgroundchanger.adapters.ImageAdapter
import com.mohamed.halim.essa.backgroundchanger.data.Image
import com.mohamed.halim.essa.backgroundchanger.data.PhotoViewModel
import com.mohamed.halim.essa.backgroundchanger.databinding.FragmentPhotosBinding


class PhotosFragment : Fragment() {
    lateinit var viewModel : PhotoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        val binding = FragmentPhotosBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val recyclerView = binding.imageRecycleView
        val adapter = ImageAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(),5)
        viewModel.images.observe(viewLifecycleOwner,
            Observer { t -> adapter.swapList(t!!) })
        return binding.root
    }

}
