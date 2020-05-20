package com.mohamed.halim.essa.backgroundchanger.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.mohamed.halim.essa.backgroundchanger.databinding.FragmentImageViewBinding
import timber.log.Timber

class ImageViewFragment : Fragment() {
    lateinit var image: ImageView
    lateinit var detector: GestureDetectorCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentImageViewBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.imageUrl = arguments?.getString("imageUrl")
        Timber.d(arguments?.getString("imageUrl"))
        image = binding.fullImageView

        return binding.root
    }


}
