package ayupov.ilgam.mcache.presentation.audio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ayupov.ilgam.mcache.App
import ayupov.ilgam.mcache.domain.MediaItem
import ayupov.ilgam.mcache.domain.MediaType.AUDIO
import ayupov.ilgam.mcache.domain.Source.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AudioViewModel : ViewModel() {

    private val repository = App.repository

    private val audios: MutableLiveData<List<MediaItem>> by lazy {
        MutableLiveData<List<MediaItem>>().also {
            loadAudio()
        }
    }

    fun getAudios() = audios as LiveData<List<MediaItem>>

    fun loadAudio() {
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val mediaItems = repository.getMediaItems(AUDIO, listOf(TELEGRAM, WHATSAPP, VIBER))
            audios.postValue(mediaItems)
        }
    }
}
