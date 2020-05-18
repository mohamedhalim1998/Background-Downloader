package com.mohamed.halim.essa.backgroundchanger.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamed.halim.essa.backgroundchanger.network.ApiService
import com.mohamed.halim.essa.backgroundchanger.network.PhotosApi
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PhotoViewModel : ViewModel(){
    private val _response = MutableLiveData<String>()
    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    init {
        getPhotos()
    }

    private fun getPhotos() {
        PhotosApi.retrofitService.getPhotos().enqueue(object : Callback<List<Image>>{
            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                Timber.d(t)
                _response.value = "failed"
            }

            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                _response.value = response.body()?.size.toString()
            }

        })
    }
}