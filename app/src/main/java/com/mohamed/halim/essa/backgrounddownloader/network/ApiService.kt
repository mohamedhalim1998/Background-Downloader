package com.mohamed.halim.essa.backgrounddownloader.network

import com.mohamed.halim.essa.backgrounddownloader.data.Image
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.unsplash.com/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ApiService {
    @GET("photos?client_id=USE_YOURS&client_secret=USE_YOURS")
    fun getPhotos(@Query("page") pageNum: Int): Call<List<Image>>
    @Streaming
    @GET
    fun downloadImage(@Url fileUrl : String) : Call<ResponseBody>
}

object PhotosApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}