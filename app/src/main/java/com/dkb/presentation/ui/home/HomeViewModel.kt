package com.dkb.presentation.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dkb.domain.entity.response.Photos
import com.dkb.domain.usecase.photos.GetPhotosUseCase
import com.dkb.presentation.ui.base.BaseViewModel

class HomeViewModel(private val getPhotosUseCase: GetPhotosUseCase): BaseViewModel() {

    private val images = MutableLiveData<Photos>()

    init {
        fetchPhotos()
    }

    fun getImages(): MutableLiveData<Photos> {
        return images
    }

     private fun fetchPhotos() = launchUnit {
        getPhotosUseCase.getPhotos().doOnSuccess {
            images.postValue(it)
        }
    }
}