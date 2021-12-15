package com.example.photosearching

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Bağımlılıkların eklenmesi tamamlandıktan sonra Hilt’in application classımızda @HiltAndroidApp annotasyonu ile belirtilmelidir ve Manifest’e application classımızı tanımlamalıyız. Bu bileşenlerin oluşturulabilmesi için gereklidir.
@HiltAndroidApp
class PhotoSearchingApplication : Application() {
}