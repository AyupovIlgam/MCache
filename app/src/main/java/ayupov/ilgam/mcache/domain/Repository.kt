package ayupov.ilgam.mcache.domain

interface Repository {

    suspend fun getMediaItems(mediaType: MediaType, sources: List<Source>): List<MediaItem>
}
