package com.example.photosearching.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.photosearching.repository.UnsplashRepository
import com.example.photosearching.other.Constants.DEFAULT_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository
    ): ViewModel() {

        private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    // Buradaki switchMap i kullanmamızın sebebi filtreleme yani arama özellliği olan temel bir arama özelliği uygulamasıdır.
    // Arama metni her değiştiğinde arama sonuçları güncellenmek istediğinde kullanılır.
        val photos = currentQuery.switchMap{queryString ->
            repository.getSearchResults(queryString).cachedIn(viewModelScope)
        }

    // burada search yapamak istediğimiz photoların isimlerine göre filtreleme yapmak için gerekli string değerleri alıyoruz. Yani UI daki Search Menu de yazılan filtreleme için yazılan stringleri allıyoruz.
        fun searchPhotos(query: String){
            currentQuery.value = query
        }
}