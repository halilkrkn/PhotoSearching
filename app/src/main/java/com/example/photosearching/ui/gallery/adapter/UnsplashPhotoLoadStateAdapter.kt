package com.example.photosearching.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photosearching.databinding.UnsplashPhotoLoadStateFooterBinding


// Burada adapter içerisindeki verilerin internet gittiğinde footer ve header kısımlarında hata mesajı gibi gözükmesi ve internet geldiğiğinde retry butonu ile tekrardan verilerin çekilkmesi istenmesi için yapılmış bir adapterdir.
// Bu özellik Paging 3 Kütüphane yani pagination içerisinde mevcut bir özelliktir. Bu şekilde load işlemi için header ve footer kıısımlarında verilerin çekilmesi için olluşturmuş ollduğumuz xml dosyası içerisndeki görüntüler gözükmektedir ve bu burdaki görüntüler sayesinde error mesajı igib karşımıza çıkıp Retry butonu ille de internet geldiğinde verileri tekrardan çekebilliyoruz..

class UnsplashPhotoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnsplashPhotoLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //retry methodu ile paginationın içerisinde var olan retry methodunu Gallary Fragmentte alııp buradaki retry methoduna aktardıık. Böylelikle UI da karşımıza çıkan butonu kullanabilir özelllliğie kabvuşturduk ve internet bağlantısı tekrar geldiğinde verileri tekrar çekme işlemi yapılmış oldu.
        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }


        // Bu kısımda olluşturmuş olduğumuz UnsplashPhotoLoadStateFooter xml i içerisindeki uıları gözükmesini sağllattık ama veri yoksa buton ve textviewler gözüküyor. progress bar ise her yükleme sonunna gellindiğinde ortaya çıkıp yüklllemeyi yineliyor.

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBarLoadState.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = UnsplashPhotoLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadStateViewHolder(binding)

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


}