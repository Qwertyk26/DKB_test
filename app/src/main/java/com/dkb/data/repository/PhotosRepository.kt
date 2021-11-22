package com.dkb.data.repository

import com.dkb.domain.entity.response.Photos
import com.dkb.domain.repository.IPhotosRepository

class PhotosRepository: AbstractRepository(), IPhotosRepository {
    override suspend fun getPhotos(): Photos {
        return server.getPhotos()
    }
}