package com.example.photosearching.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.photosearching.R
import com.example.photosearching.databinding.FragmentGalleryBinding
import com.example.photosearching.ui.gallery.adapter.UnsplashPhotoAdapter
import com.example.photosearching.ui.gallery.adapter.UnsplashPhotoLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint annotation'ı ile activityde Hilt'i etkinleştirin
@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery)/*,OnItemClickListener*/{

    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FragmentGalleryBinding asllında llayout içerisndeki fragment_gallery.xmllll in bir bir viewBinding Sınıfı ve onu da Fragmente bağladık.
        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter(/*this*/)
        // RecyclerView ı Fragmente bağladık.
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(

                // Burada withLoadStateHeaderAndFooter methodu ile hearder ve footer özelliklerini fragmentte tanımlamış olduk ve bu özellik PagingDataAdapter içerisinde hazır mevcut bir methottur. Burada Retry özellliğini PagingDataAdapter içerisinde çağırdık ve UnsplashPhotoLoadStateAdapter içerisindeki retry a tanımladık.
                header = UnsplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { adapter.retry() },
            )

            // Uygulama açıldığında ve uygullama içerisinde eğer internet erişimi gittiğinde hata mesajı ille karşılaşıp retry butonu ile internet erişimi geldiğğinde tekrardan verileri almak için butonu aktifleştirdik.
            buttonRetry.setOnClickListener {
                adapter.retry()
            }

        }

        //viewmodel tanımlamış olduğumuz photos dan verileri allarak adaptera gönderip fragment üzerinde yani UI da fotoları gösterdik.
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }


        //Burada ise internet erişimi uygulamayı açtığımızda hiç yoksa veya veriler uygulama açılldığında yükllenmediyse ve uygulama içerisinde gittiğinde ve yine filltrelleme yapıldığında veriler alamayacağı için uyarı mesajı gibi bir durum karşımıza çıkacak ve ordan retry işllemi ile eğer internet erişimi geri sağlanırsa verileri tekrardan geri çağıracaz.
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                //Empty View - Buradaki amaç ise uygulama içerisinde filtrelleme yaparken alakasız aramalar yapıldığıda boş bir ekrandan ziyada uyarı mesajı ile kullllanıccıyı uyar işlemi yapıldı.
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        // Menuyü fragmentte bind ettik yani bağladık.
        setHasOptionsMenu(true)
    }

    // Sayfa içerisinde görüntülere tıklayıp detaylara gitmemiz için click işlemi yapılldı ve nagivate işlemi ilede detail sayfasına yönlendirme yapıldı.
//    override fun onItemClick(photo: UnsplashPhoto) {
//        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
//        findNavController().navigate(action)
//    }


    // SearchView çağırılarak Search menü nin kurulumu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        // Menuyü Uı da gösterdik
        inflater.inflate(R.menu.menu_gallery, menu)

        // Menu içerisinde filtreleme yapmak için SearchView Methodunu çağırdık ve kullandık.
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        // Search menu kısmanda istenilen armayaı filtrelemeyi yapmak için kullanıcını yazdığını dinliyor ve Uı onu filtrelemeye özgü sonuçları gösteriyor
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    // ViewBinding işlemi yapıldığğında onDestroyView methodunu çağırıp bindig i null olarak belirtmemiz geerek çünkü Galery Fragmentte crashlenme olmaması için
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}