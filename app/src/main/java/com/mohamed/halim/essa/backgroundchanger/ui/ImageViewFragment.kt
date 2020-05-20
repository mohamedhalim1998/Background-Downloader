package com.mohamed.halim.essa.backgroundchanger.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mohamed.halim.essa.backgroundchanger.data.Image
import com.mohamed.halim.essa.backgroundchanger.databinding.FragmentImageViewBinding
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ImageViewFragment : Fragment() {
    lateinit var imageView: ImageView
    lateinit var image: Image
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)


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

    class DownloadTask(private val context: Context, private val id: String, val url: String) :
        AsyncTask<Unit, Unit, Unit>() {
        var isSaved : Boolean = false;
        override fun onPreExecute() {
            super.onPreExecute()
            Toast.makeText(context, "saving...", Toast.LENGTH_SHORT).show();

        }

        override fun doInBackground(vararg params: Unit?) {

            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
                        + "/" + id + ".jpg"
            )
            if(file.exists()){
                isSaved = true
                return
            }
            try {
                file.createNewFile()
                val ostream = FileOutputStream(file)
                val bitmap = Picasso.get().load(url).get()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream)
                ostream.flush()
                ostream.close()
                Timber.d("done ${file.name}  at size  ${(file.totalSpace / 1024)}")
            } catch (e: IOException) {
                Timber.e(e.localizedMessage)
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            Timber.d("Done")
            if(isSaved){
                Toast.makeText(context, "already saved", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
