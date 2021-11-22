package com.dkb.domain.repository

import com.dkb.domain.entity.response.Photos

interface IPhotosRepository: ICommonRepository {
    suspend fun getPhotos(): Photos
}