package ayupov.ilgam.mcache.presentation.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ayupov.ilgam.mcache.App
import ayupov.ilgam.mcache.domain.MediaItem
import ayupov.ilgam.mcache.domain.MediaType.VIDEO
import ayupov.ilgam.mcache.domain.Source.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    private val repository = App.repository

    private val videos: MutableLiveData<List<MediaItem>> by lazy {
        MutableLiveData<List<MediaItem>>().also {
            loadVideo()
        }
    }

    fun getVideos() = videos as LiveData<List<MediaItem>>

    fun loadVideo() {
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val mediaItems = repository.getMediaItems(VIDEO, listOf(TELEGRAM, WHATSAPP, VIBER))
            videos.postValue(mediaItems)
        }
    }
}
