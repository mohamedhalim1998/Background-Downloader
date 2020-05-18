package com.mohamed.halim.essa.backgroundchanger.data

import com.squareup.moshi.Json

data class Image @JvmOverloads constructor(
    val id: String,
    @Json(name = "created_at") val createdAt: String? = null,
    @Json(name = "updated_at") val updatedAt: String? = null,
    @Json(name = "promoted_at") val promotedAt: String? = null,
    val width: Int? = null,
    val height: String? = null,
    val description: String? = null,
    @Json(name = "alt_description") val altDescription: String? = null,
    val urls: Urls? = null,
    val links: Links? = null,
    val likes: Int? = null


)

data class Urls @JvmOverloads constructor(
    val raw: String? = null,
    val full: String? = null,
    val regular: String? = null,
    val small: String? = null,
    val thumb: String? = null
)

data class Links @JvmOverloads constructor(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,
    @Json(name = "download_location") val downloadLocation: String? = null
)