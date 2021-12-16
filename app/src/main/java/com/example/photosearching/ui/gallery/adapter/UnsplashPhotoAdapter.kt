package com.example.photosearching.ui.gallery.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.photosearching.R
import com.example.photosearching.data.models.UnsplashPhoto
import com.example.photosearching.databinding.ItemUnsplashPhotoBinding
import com.example.photosearching.ui.gallery.GalleryFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_details.view.*


class UnsplashPhotoAdapter(/*private val listener: OnItemClickListener*/) :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    // Buradaki ItemUnsplashPhotoBinding sınıfı aslında item_unsplash_photo.xml dosyasının bir otomatik olarak  oluşturulan ViewBinding Sınıfıdır.
    //    Mesela siz activity_splash.xml adında bir layout oluşturursunuz, arkaplanda otomatik olarak ActivitySplashBinding.kt binding nesnesi oluşturulur.
    // Bunu yapabilmemiz içib builld.gradle(:app) in içerisinde viewBindig true olarak yapılandırmak.
    inner class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Bu kısma UI da göstermek istediğimiz şeylerin komutlarını yazıp UI da gösteriyoruz.
        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(imageViewItemUnsplashPhoto)

                textViewUsername.text = photo.user.name

            }
        }


        // burada asıl UnsplashPhotoAdapter recyclerview ı içerisnde tıklama işleminin yapıldığı yani click özelliğinin getirildiği yer yer .
//        init {
//            binding.root.setOnClickListener {
//                val position = bindingAdapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val item = getItem(position)
//                    if (item != null) {
////                        listener.onItemClick(item)
//                    }
//                }
//            }
//        }


    }

    // burada OnItemClickListener dan interface oluşturup ve içerisine UnsplashPhoto dan verileri alması gerektiği içinde OnItemClick Adında fonskiyon methodu uyguladık.
    // UnsplashPhotoAdapter a constructer işlemi yapıldı ve OnItemClickListener ı çağırılldı.
//    interface OnItemClickListener {
//        fun onItemClick(photo: UnsplashPhoto)
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
        // Burada popup ekranı ile detay(detail) sayfası gözükmektedir.
        holder.itemView.apply {

            // Yani gallery ekranı içerisinde herhangi bir fotoya tıklandığında o fotonun detay sayfası popup şeklinde karşımıza çıkmaktadır.
            setOnClickListener {
                val bottomSheetDialog = BottomSheetDialog(
                    it.context,R.style.Theme_MaterialComponents_Light_BottomSheetDialog
                )
                val bottomSheetView = LayoutInflater.from(it.context).inflate(R.layout.fragment_details_popup,
                    findViewById<FrameLayout>(R.id.bottomSheets)
                )

                bottomSheetView.apply {
                    text_view_description.text = currentItem?.description
                    text_view_creator.text = currentItem?.user?.name

                    Glide.with(this)
                        .load(currentItem?.urls?.regular)
                        .error(R.drawable.ic_baseline_image_not_supported_24)
                        // Bu listener kısmında ise görüntüler eğer tamamen gelmezse ve ulaşılamazsa diye dinleme yapıyor ve eğer görüntü yoksa sadece error özellik döner ve yazılar dahi gözükmez.
                        .listener(object : RequestListener<Drawable> {

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progress_bar.isVisible = false
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progress_bar.isVisible = false
                                text_view_creator.isVisible = true
                                text_view_description.isVisible = currentItem?.description != null
                                return false
                            }

                        })
                        .into(image_view)


                    val uri = Uri.parse(currentItem?.user?.attributionUrls)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    text_view_creator.apply {
                        text = "Creator Profile: Photo by ${currentItem?.user?.name} on Unsplash"
                        setOnClickListener {
                            context.startActivity(intent)
                        }
                        paint.isUnderlineText = true
                    }

                }
                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()

            }

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
                oldItem: UnsplashPhoto, newItem: UnsplashPhoto
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}