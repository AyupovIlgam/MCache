package ayupov.ilgam.mcache.presentation.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ayupov.ilgam.mcache.App
import ayupov.ilgam.mcache.domain.MediaItem
import ayupov.ilgam.mcache.domain.MediaType.IMAGES
import ayupov.ilgam.mcache.domain.Source.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesViewModel : ViewModel() {

    private val repository = App.repository

    private val images: MutableLiveData<List<MediaItem>> by lazy {
        MutableLiveData<List<MediaItem>>().also {
            loadImages()
        }
    }

    fun getImages() = images as LiveData<List<MediaItem>>

    fun loadImages() {
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val mediaItems = repository.getMediaItems(IMAGES, listOf(TELEGRAM, WHATSAPP, VIBER))
            images.postValue(mediaItems)
        }
    }
}
