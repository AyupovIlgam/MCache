package ayupov.ilgam.mcache.presentation.media

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import ayupov.ilgam.mcache.databinding.LayoutMediaItemBinding
import ayupov.ilgam.mcache.domain.MediaItem

class MediaItemsAdapter(
    context: Context,
) : Adapter<MediaItemViewHolder>() {

    private val items = mutableListOf<MediaItem>()
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MediaItemViewHolder {
        val binding = LayoutMediaItemBinding.inflate(layoutInflater, viewGroup, false)
        return MediaItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: MediaItemViewHolder, position: Int) =
        viewHolder.bind(items[position])

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<MediaItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
