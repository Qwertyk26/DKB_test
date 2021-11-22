package com.dkb.data.server

import com.dkb.domain.entity.response.Photos
import retrofit2.http.GET

interface ServerApi {
    @GET("photos")
    suspend fun getPhotos(): Photos
}