package ayupov.ilgam.mcache.presentation.media

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ayupov.ilgam.mcache.R
import ayupov.ilgam.mcache.databinding.LayoutMediaItemBinding
import ayupov.ilgam.mcache.domain.MediaItem

class MediaItemViewHolder(
    private val binding: LayoutMediaItemBinding,
) : ViewHolder(binding.root) {

    fun bind(mediaItem: MediaItem) {
        binding.run {
            tvName.text = mediaItem.name
            tvExtension.text = mediaItem.extension
            tvSize.text =
                itemView.context.resources.getString(R.string.kilobytes, mediaItem.sizeInKilobytes)
        }
    }
}
