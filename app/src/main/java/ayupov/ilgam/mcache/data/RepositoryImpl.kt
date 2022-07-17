package ayupov.ilgam.mcache.data

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.Q
import android.provider.MediaStore.*
import android.provider.MediaStore.MediaColumns.*
import ayupov.ilgam.mcache.domain.MediaItem
import ayupov.ilgam.mcache.domain.MediaType
import ayupov.ilgam.mcache.domain.MediaType.*
import ayupov.ilgam.mcache.domain.Repository
import ayupov.ilgam.mcache.domain.Source

class RepositoryImpl(
    private val applicationContext: Context,
) : Repository {

    override suspend fun getMediaItems(
        mediaType: MediaType,
        sources: List<Source>,
    ): List<MediaItem> {
        val list = mutableListOf<MediaItem>()

        val contentResolver = applicationContext.contentResolver

        val collection = when (mediaType) {
            VIDEO -> collectionVideo
            AUDIO -> collectionAudio
            IMAGES -> collectionImages
        }

        val projection = arrayOf(DISPLAY_NAME, SIZE)

        val selectionStringBuilder = StringBuilder()

        val selectionArgs = arrayOfNulls<String>(sources.size)

        sources.forEachIndexed { index, source ->
            selectionStringBuilder.append("$DATA like ?")
            if (index != sources.size - 1)
                selectionStringBuilder.append(" OR ")
            selectionArgs[index] = "%${source.packageName}%"
        }

        val cursor = contentResolver.query(
            collection,
            projection,
            selectionStringBuilder.toString(),
            selectionArgs,
            null,
        )

        cursor?.use {
            val columnDisplayName = it.getColumnIndexOrThrow(DISPLAY_NAME)
            val columnSize = it.getColumnIndexOrThrow(SIZE)
            while (it.moveToNext()) {
                val displayName = it.getString(columnDisplayName)
                val name = displayName.substringBeforeLast(".")
                val extension = displayName.substringAfterLast(".")
                val sizeInKilobytes = it.getInt(columnSize) / 1000
                list.add(MediaItem(name, extension, sizeInKilobytes))
            }
        }

        return list
    }

    companion object {
        private val collectionVideo = if (SDK_INT >= Q) {
            Video.Media.getContentUri(VOLUME_EXTERNAL)
        } else {
            Video.Media.EXTERNAL_CONTENT_URI
        }

        private val collectionAudio = if (SDK_INT >= Q) {
            Audio.Media.getContentUri(VOLUME_EXTERNAL)
        } else {
            Audio.Media.EXTERNAL_CONTENT_URI
        }

        private val collectionImages = if (SDK_INT >= Q) {
            Images.Media.getContentUri(VOLUME_EXTERNAL)
        } else {
            Images.Media.EXTERNAL_CONTENT_URI
        }
    }
}
