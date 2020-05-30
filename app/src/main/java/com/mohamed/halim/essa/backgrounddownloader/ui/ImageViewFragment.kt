package com.mohamed.halim.essa.backgrounddownloader.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mohamed.halim.essa.backgrounddownloader.R
import com.mohamed.halim.essa.backgrounddownloader.data.Image
import com.mohamed.halim.essa.backgrounddownloader.databinding.FragmentImageViewBinding
import com.mohamed.halim.essa.backgrounddownloader.network.DownloadTask
import com.mohamed.halim.essa.backgrounddownloader.network.ShareTask
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
        setHasOptionsMenu(true)

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
