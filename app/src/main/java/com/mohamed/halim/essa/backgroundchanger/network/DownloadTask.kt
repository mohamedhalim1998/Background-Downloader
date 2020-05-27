package com.mohamed.halim.essa.backgroundchanger.network

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Environment
import android.widget.Toast
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DownloadTask(private val context: Context, private val id: String, val url: String) :
    AsyncTask<Unit, Unit, Unit>() {
    var isSaved: Boolean = false;
    override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(context, "saving...", Toast.LENGTH_SHORT).show();

    }

    override fun doInBackground(vararg params: Unit?) {

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
                    + "/" + id + ".jpg"
        )
        if (file.exists()) {
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
        if (isSaved) {
            Toast.makeText(context, "already saved", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        }
    }
}