package com.mohamed.halim.essa.backgrounddownloader.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.backgrounddownloader.network.PhotosApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PhotoViewModel : ViewModel() {
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
                Timber.e(t)
            }

            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                _images.value?.addAll(response.body()!!)
                _images.value = _images.value
            }
        })
    }
}