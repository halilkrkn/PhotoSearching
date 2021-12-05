package com.example.photosearching.api

import com.example.photosearching.api.service.UnsplashApiService
import com.example.photosearching.models.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
) {

}