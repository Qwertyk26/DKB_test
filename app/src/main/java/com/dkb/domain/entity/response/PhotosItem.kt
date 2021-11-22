package com.dkb.domain.entity.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotosItem(
    var albumId: Int? = null,
    var id: Int? = null,
    var thumbnailUrl: String? = null,
    var title: String? = null,
    var url: String? = null
): Parcelable