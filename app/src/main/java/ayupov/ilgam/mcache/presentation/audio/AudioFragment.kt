package ayupov.ilgam.mcache.presentation.audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ayupov.ilgam.mcache.databinding.FragmentAudioBinding
import ayupov.ilgam.mcache.presentation.media.MediaItemsAdapter

class AudioFragment : Fragment() {

    companion object {

        fun newInstance() = AudioFragment()
    }

    private var binding: FragmentAudioBinding? = null
    private val audioViewModel: AudioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAudioBinding.inflate(inflater, container, false)

        binding?.root?.run {
            val mediaItemsAdapter = MediaItemsAdapter(context)
            audioViewModel.getAudios().observe(viewLifecycleOwner) {
                mediaItemsAdapter.setItems(it)
            }
            adapter = mediaItemsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
