package com.example.photosearching.repository

import com.example.photosearching.api.service.UnsplashApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // Nesneye her ulaştığımızda hep aynı nesneyi verecektir bize sürekli yeniden create etmez .
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApiService){
}