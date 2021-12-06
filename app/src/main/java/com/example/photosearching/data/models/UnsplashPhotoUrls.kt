package com.example.photosearching.data.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhotoUrls(
    val raw: String,
    val fullUrl: String,
    val regular: String,
    val small: String,
    val thumb: String
): Parcelable