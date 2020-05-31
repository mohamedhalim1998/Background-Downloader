package com.mohamed.halim.essa.backgrounddownloader.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.mohamed.halim.essa.backgrounddownloader.R
import com.mohamed.halim.essa.backgrounddownloader.data.Image
import com.mohamed.halim.essa.backgrounddownloader.databinding.FragmentImageViewBinding
import com.mohamed.halim.essa.backgrounddownloader.network.DownloadTask
import com.mohamed.halim.essa.backgrounddownloader.network.ShareTask
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import timber.log.Timber
import java.lang.Exception


class ImageViewFragment : Fragment() {
    lateinit var imageView: SubsamplingScaleImageView
    lateinit var image: Image
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var screenVisible = false
        val binding = FragmentImageViewBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        image = arguments?.getParcelable("image")!!
        binding.image = image
        Timber.d(image.urls?.full)
        imageView = binding.fullImageView
        Picasso.get().load(image.urls?.regular).into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                imageView.setImage(ImageSource.bitmap(bitmap!!))
                binding.progressBarLoad.visibility = View.GONE
            }

        })
        imageView.setOnClickListener {
            if (screenVisible) {
                requireActivity().window.decorView.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_IMMERSIVE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
            } else {
                requireActivity().window.decorView.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }
        }
        requireActivity().window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            screenVisible = visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.image_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_download -> {
                DownloadTask(requireContext(), image.id, image.urls?.full!!).execute()
                return true
            }
            R.id.action_share -> {
                ShareTask(requireContext(), image.id, image.urls?.regular!!).execute()
            }


        }
        return super.onOptionsItemSelected(item)
    }
}
