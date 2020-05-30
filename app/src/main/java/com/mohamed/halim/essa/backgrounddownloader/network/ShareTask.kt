package com.mohamed.halim.essa.backgrounddownloader.network

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.widget.Toast
import androidx.core.content.FileProvider
import com.mohamed.halim.essa.backgrounddownloader.BuildConfig
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ShareTask(private val context: Context, private val id: String, val url: String) :
    AsyncTask<Unit, Unit, File>() {
    var isSaved: Boolean = false;
    override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(context, "saving...", Toast.LENGTH_SHORT).show();
    }

    override fun doInBackground(vararg params: Unit?): File? {

        val file = File(
            context.cacheDir.absolutePath
                    + "/" + id + ".jpg"
        )
        if (file.exists()) {
            isSaved = true
            return file
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
        return file
    }

    override fun onPostExecute(result: File?) {
        super.onPostExecute(result)
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "com.mohamed.halim.essa.backgroundchanger.provider",
            result!!
        )

        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "image/*"
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivities(
            arrayOf(
                Intent.createChooser(
                    sharingIntent,
                    "Share with"
                )
            )
        )

    }
}