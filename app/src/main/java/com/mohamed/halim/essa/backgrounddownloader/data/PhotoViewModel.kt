package com.mohamed.halim.essa.backgrounddownloader.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.backgrounddownloader.network.PhotosApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.util.*

class PhotoViewModel(val app: Application) : AndroidViewModel(app) {
    private val _images = MutableLiveData<MutableList<Image>>()
    val images: LiveData<MutableList<Image>>
        get() = _images

    init {
        _images.value = mutableListOf()
        getPhotos(1)

    }

    fun getPhotos(pageNumber: Int) {
        PhotosApi.retrofitService.getPhotos(pageNumber).enqueue(object : Callback<List<Image>> {
            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                val cacheFile = File(app.cacheDir, "imagesCache")
                val scanner = Scanner(cacheFile)
                scanner.useDelimiter("\\A")
                val moshi = Moshi.Builder().build()
                val imagesList = Types.newParameterizedType(List::class.java, Image::class.java)
                val adapter = moshi.adapter<List<Image>>(imagesList)
                _images.value = adapter.fromJson(scanner.next()) as MutableList<Image>
            }

            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                _images.value?.addAll(response.body()!!)
                cacheImages()
                _images.value = _images.value
            }
        })
    }

    private fun cacheImages() {
        val moshi = Moshi.Builder().build()
        val imagesList = Types.newParameterizedType(List::class.java, Image::class.java)
        val adapter = moshi.adapter<List<Image>>(imagesList)
        val cacheFile = File(app.cacheDir, "imagesCache")
        val ostream = FileOutputStream(cacheFile)
        ostream.write(adapter.toJson(_images.value).toByteArray())
        ostream.flush()
        ostream.close()

    }

}