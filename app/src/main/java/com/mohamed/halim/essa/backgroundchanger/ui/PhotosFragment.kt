package com.mohamed.halim.essa.backgroundchanger.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.halim.essa.backgroundchanger.R
import com.mohamed.halim.essa.backgroundchanger.adapters.ImageAdapter
import com.mohamed.halim.essa.backgroundchanger.data.Image
import com.mohamed.halim.essa.backgroundchanger.data.PhotoViewModel
import com.mohamed.halim.essa.backgroundchanger.databinding.FragmentPhotosBinding
import timber.log.Timber


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

    override fun onImageClickListener(imageUrl: String) {
        val bundle = Bundle()
        bundle.putString("imageUrl", imageUrl)
        navController.navigate(R.id.action_photosFragment_to_imageViewFragment, bundle)
    }

}
