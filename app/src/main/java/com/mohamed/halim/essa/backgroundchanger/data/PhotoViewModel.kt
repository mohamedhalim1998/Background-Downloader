package com.mohamed.halim.essa.backgroundchanger.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.backgroundchanger.network.PhotosApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PhotoViewModel : ViewModel() {
    private val _images = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>>
        get() = _images

    init {
        getPhotos(1)
    }

    fun getPhotos(pageNumber: Int) {
        PhotosApi.retrofitService.getPhotos(pageNumber).enqueue(object : Callback<List<Image>> {
            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                Timber.d(t)
            }

            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                _images.value = response.body()
            }
        })
    }
}