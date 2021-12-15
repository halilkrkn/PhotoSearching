package com.example.photosearching.di

import com.example.photosearching.api.service.UnsplashApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@Module annotasyonuyla bağımlılıkları sağladığımız yani nesneleri oluşturduğumuz sınıflarımızın en başına eklenir. Constructer ekleyemeyeceğimiz tipler için binding yapmamızı sağlar.
//@InstallIn annotasyonu ise modulün hangi component üzerinde bulunmasını istediğimizi belirtiriz. Componentlerin bir lifecycle’ı bulunmaktadır.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //@Provides annotasyonu modullerde nesneleri bağımlılık olarak tanımlamak için oluşturduğumuz methodların üzerinde kullanılır.
    //  @Singleton Nesneye her ulaştığımızda hep aynı nesneyi verecektir bize sürekli yeniden create etmez. Singleton  bir class’ın dışarıdan birden fazla instance(örnek, nesne) oluşturmasını engeller.
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApiService =
        retrofit.create(UnsplashApiService::class.java)


}