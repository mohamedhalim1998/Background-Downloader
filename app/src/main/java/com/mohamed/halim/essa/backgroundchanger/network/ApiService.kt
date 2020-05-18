package com.mohamed.halim.essa.backgroundchanger.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.unsplash.com/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()
interface ApiService {
    @GET("photos?page=1&client_id=hkljOHo8h7IfEc3eEhMAmmKEm-5kO10shYetG1urFsY&client_secret=ccMJq-sDW5cbKcICR4tgBk2uzFaiyrBFuaqJXaM0n-A")
    fun getPhotos() : Call<List<UnsplashPhoto>>
}