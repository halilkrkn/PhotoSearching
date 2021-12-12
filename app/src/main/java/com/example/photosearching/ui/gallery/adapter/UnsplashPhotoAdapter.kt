package com.example.photosearching.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.photosearching.R
import com.example.photosearching.data.models.UnsplashPhoto
import com.example.photosearching.databinding.ItemUnsplashPhotoBinding

class UnsplashPhotoAdapter :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    // Buradaki ItemUnsplashPhotoBinding sınıfı aslında item_unsplash_photo.xml dosyasının bir otomatik olarak  oluşturulan ViewBinding Sınıfıdır.
    //    Mesela siz activity_splash.xml adında bir layout oluşturursunuz, arkaplanda otomatik olarak ActivitySplashBinding.kt binding nesnesi oluşturulur.
    // Bunu yapabilmemiz içib builld.gradle(:app) in içerisinde viewBindig true olarak yapılandırmak.
    class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
            // Bu kısma UI da göstermek istediğimiz şeylerin komutlarını yazıp UI da gösteriyoruz.
            fun bind(photo: UnsplashPhoto){
                binding.apply {
                    Glide.with(itemView)
                        .load(photo.urls.regular)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_baseline_cancel_presentation_24)
                        .into(imageViewItemUnsplashPhoto)

                    textViewUsername.text = photo.user.name
                }
            }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null){
            holder.bind(currentItem)
        }
    }


    // DiffUtil, RecyclerView adapterındaki verilerin daha verimli bir şekilde güncellenmesi için kullanılır.
    //  RecyclerView’daki veriyi güncellemek veya filtreleme ihtiyacımız olabiliyor. En verimlli yöntem DiffUtili kulanmaktır.
    //   DiffUtil iki liste arasındaki farkı hesaplayıp bize güncel listeyi veren bir utility sınıfıdır. DiffUtil iki listeyi karşılaştırıp minimum güncelleme sayısını hesaplamak için Eugene W. Myers‘in fark algoritmasını kullanıyor.
    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {

            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
                return oldItem == newItem
            }

        }
    }


}