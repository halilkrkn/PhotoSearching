package com.example.photosearching.api

import com.example.photosearching.data.models.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)