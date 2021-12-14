package com.example.photosearching.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.photosearching.api.service.UnsplashApiService
import com.example.photosearching.data.pagination.UnsplashPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // Nesneye her ulaştığımızda hep aynı nesneyi verecektir bize sürekli yeniden create etmez .
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApiService){

    fun getSearchResults(query: String) =
        Pager(
            // Burada pagination olan sayfalar yapılandırıldı.
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            // UnsplashPagingSource u repository e ekledik.
            pagingSourceFactory = {
                UnsplashPagingSource(unsplashApi, query)
            }
        ).liveData

}