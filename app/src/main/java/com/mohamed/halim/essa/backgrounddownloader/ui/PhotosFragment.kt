package com.mohamed.halim.essa.backgrounddownloader.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.halim.essa.backgrounddownloader.R
import com.mohamed.halim.essa.backgrounddownloader.adapters.ImageAdapter
import com.mohamed.halim.essa.backgrounddownloader.data.Image
import com.mohamed.halim.essa.backgrounddownloader.data.PhotoViewModel
import com.mohamed.halim.essa.backgrounddownloader.databinding.FragmentPhotosBinding


class PhotosFragment : Fragment(), ImageAdapter.ImageClickListener {
    lateinit var viewModel: PhotoViewModel
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN)
        viewModel = ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        val binding = FragmentPhotosBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val recyclerView = binding.imageRecycleView
        val adapter = ImageAdapter(this)
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 3)
        if(requireContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager.spanCount = 5
        }
        recyclerView.layoutManager = layoutManager
        viewModel.images.observe(viewLifecycleOwner,
            Observer { t ->
                adapter.swapList(t!!)
            })
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.getPhotos(page)
            }

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onImageClickListener(image: Image) {
        val bundle = Bundle()
        bundle.putParcelable("image", image)
        navController.navigate(R.id.action_photosFragment_to_imageViewFragment, bundle)
    }

}
