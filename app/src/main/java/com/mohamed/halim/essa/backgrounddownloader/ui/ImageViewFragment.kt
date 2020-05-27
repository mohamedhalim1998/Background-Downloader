package com.mohamed.halim.essa.backgrounddownloader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mohamed.halim.essa.backgrounddownloader.data.Image
import com.mohamed.halim.essa.backgrounddownloader.databinding.FragmentImageViewBinding
import com.mohamed.halim.essa.backgrounddownloader.network.DownloadTask
import timber.log.Timber



class ImageViewFragment : Fragment() {
    lateinit var imageView: ImageView
    lateinit var image: Image
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_FULLSCREEN)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentImageViewBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        image = arguments?.getParcelable("image")!!
        binding.image = image
        Timber.d(image.urls?.full)
        imageView = binding.fullImageView
        binding.root.setOnLongClickListener {
            downloadImage()
            true
        }
        return binding.root
    }

    private fun downloadImage() {
        Timber.d("start")
        DownloadTask(requireContext(), image.id, image.urls?.full!!).execute()
    }
}
