package com.example.photosearching.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// TODO: 5.12.2021
//  Parelize: Android için  sayfalar arası daha hızlı veri transferi yapabilmek için Parcelize geliştirildi.
// Androidde verileri bir yerden bir yere paslıyorsak ve o veriyi nesne olarak göndereceksek o verinin Parcelable ya da Serializable interfacelerinden birini implement edilmesi gerek. Serializable düşük performans veriyor.
@Parcelize
data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashPhotoUser
) : Parcelable
