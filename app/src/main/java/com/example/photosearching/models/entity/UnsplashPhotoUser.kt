package com.example.photosearching.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhotoUser(
    val id: String,
    val username: String,
    val name: String,
    @SerializedName("instagram_username")
    val instagramUsername: String,
    @SerializedName("twitter_username")
    val twitterUsername: String
) : Parcelable {
    val attributionUrls get() = "htts://unsplash.com/$username?utm_sourse=PhotoSearching&utm_medium=referral"

}