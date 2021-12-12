package com.example.photosearching.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.photosearching.R
import com.example.photosearching.databinding.FragmentGalleryBinding
import com.example.photosearching.ui.gallery.adapter.UnsplashPhotoAdapter
import com.example.photosearching.ui.gallery.adapter.UnsplashPhotoLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint annotation'ı ile activityde Hilt'i etkinleştirin
@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery){

    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() =  _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FragmentGalleryBinding asllında llayout içerisndeki fragment_gallery.xmllll in bir bir viewBinding Sınıfı ve onu da Fragmente bağladık.
        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter()
        // RecyclerView ı Fragmente bağladık.
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(

                // Burada withLoadStateHeaderAndFooter methodu ile hearder ve footer özelliklerini fragmentte tanımlamış olduk ve bu özellik PagingDataAdapter içerisinde hazır mevcut bir methottur. Burada Retry özellliğini PagingDataAdapter içerisinde çağırdık ve UnsplashPhotoLoadStateAdapter içerisindeki retry a tanımladık.
                header = UnsplashPhotoLoadStateAdapter{adapter.retry()},
                footer = UnsplashPhotoLoadStateAdapter{adapter.retry()},

            )


        }

        //viewmodel tanımlamış olduğumuz photos dan verileri allarak adaptera gönderip fragment üzerinde yani UI da fotoları gösterdik.
        viewModel.photos.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}