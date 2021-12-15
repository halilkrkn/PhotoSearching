package com.example.photosearching.ui.gallery

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.photosearching.repository.UnsplashRepository
import com.example.photosearching.other.Constants.DEFAULT_QUERY
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository,
    state: SavedStateHandle
    ): ViewModel() {


        // herhangi bir görüntüye tıkalayıp detay sayfasına girdiğimizde uygulamayı alta alıp tekrar girdiğimizde ve detay sayfasıyla yine başlayıp sonra geri gallery sayfasına dönüldüğünde uygulama ilk açıldığı sayfaya dönmektedir.
       // O yüzden en son filtreleme yapıldığında hangi arama yapılldıysa oraya geri dönmek için böyle bir yöntem kullanıldı.
        private val currentQuery = state.getLiveData(CURRENT_QUERY,DEFAULT_QUERY)

    // Buradaki switchMap i kullanmamızın sebebi filtreleme yani arama özellliği olan temel bir arama özelliği uygulamasıdır.
    // Arama metni her değiştiğinde arama sonuçları güncellenmek istediğinde kullanılır.
        val photos = currentQuery.switchMap{queryString ->
            repository.getSearchResults(queryString).cachedIn(viewModelScope)
        }

    // burada search yapamak istediğimiz photoların isimlerine göre filtreleme yapmak için gerekli string değerleri alıyoruz. Yani UI daki Search Menu de yazılan filtreleme için yazılan stringleri allıyoruz.
        fun searchPhotos(query: String){
            currentQuery.value = query
        }

    companion object{
        private const val CURRENT_QUERY = "current_query"
    }
}