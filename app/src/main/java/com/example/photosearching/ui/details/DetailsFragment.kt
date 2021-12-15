package com.example.photosearching.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.photosearching.R
import com.example.photosearching.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    // Burada detail fragment için navigation içerisinde photo sınıfını argüment olarak tanımladık ve photo sınıfın çağırabilmek için ise detailArgs adında değişken oluşturup navArgs yardımıyla aldık.
    private val detailArgs by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fragmenti bind ettik yani view e bağldık.
        val bindingDetailsFragment = FragmentDetailsBinding.bind(view)

        bindingDetailsFragment.apply {
            val photo =
                detailArgs.photo // Bu kısımda oluşturmuş olduğumuz detailArg değişkeni sayesinde photo yani UnsplashPhoto sınıfına ulaştık ki içerisidneki değerleri kullanabilmek için.
            Glide.with(this@DetailsFragment)
                .load(photo.urls.regular)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                // Bu listener kısmında ise görüntüler eğer tamamen gelmezse ve ulaşılamazsa diye dinleme yapıyor ve eğer görüntü yoksa sadece error özellik döner ve yazılar dahi gözükmez.
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        textViewDescription.isVisible = photo.description != null
                        return false
                    }

                })
                .into(imageView)

            textViewDescription.text = photo.description

            // Bu kısımda ise unsplash içerisinde photo yükleyen creatorların hesaplarına erişebilmek için url adreslerini gösterdik ve üzerine tıklandığında oo cretorun unsplash profiline yönlendiriliyor.
            val uri = Uri.parse(photo.user.attributionUrls)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            textViewCreator.apply {
                text = "Photo by ${photo.user.name} on Unsplash"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }
    }
}