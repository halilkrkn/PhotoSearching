package com.example.photosearching.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.photosearching.api.service.UnsplashApiService
import com.example.photosearching.data.models.UnsplashPhoto
import com.example.photosearching.other.Constants.UNSPLASH_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApiService,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    // apiden alacağımız request(istekleri) buradan sayfaya yüklüyoruz.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {

        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.getSearchPhoto(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }
}