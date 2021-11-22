package com.dkb.domain.usecase.photos

import com.dkb.domain.repository.IPhotosRepository
import com.dkb.domain.usecase.AbstractUseCase

class GetPhotosUseCase(private val repo: IPhotosRepository): AbstractUseCase() {

    suspend fun getPhotos() = execute {
        return@execute repo.getPhotos()
    }
}