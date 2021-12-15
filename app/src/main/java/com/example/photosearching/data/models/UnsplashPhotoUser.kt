package com.example.photosearching.data.models

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

    // Burada url ile detail sayfası(Detail Fragment) içerisindeki url i tanımlamış olduk ve görüntüyü yükleyen kullanıcının unsplash hesabındaki profilene yönlendirmek için böyle bir url oluşturuldu.
    val attributionUrls get() = "https://unsplash.com/$username?utm_source=PhotoSearching&utm_medium=referral"

}
