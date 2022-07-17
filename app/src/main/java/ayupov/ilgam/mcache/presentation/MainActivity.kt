package ayupov.ilgam.mcache.presentation

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.requestPermissions
import ayupov.ilgam.mcache.R
import ayupov.ilgam.mcache.databinding.ActivityMainBinding
import ayupov.ilgam.mcache.presentation.audio.AudioFragment
import ayupov.ilgam.mcache.presentation.audio.AudioViewModel
import ayupov.ilgam.mcache.presentation.image.ImagesFragment
import ayupov.ilgam.mcache.presentation.image.ImagesViewModel
import ayupov.ilgam.mcache.presentation.video.VideoFragment
import ayupov.ilgam.mcache.presentation.video.VideoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val videoViewModel: VideoViewModel by viewModels()
    private val audioViewModel: AudioViewModel by viewModels()
    private val imagesViewModel: ImagesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.action_video -> VideoFragment.newInstance()
                R.id.action_audio -> AudioFragment.newInstance()
                R.id.action_image -> ImagesFragment.newInstance()
                else -> throw IllegalArgumentException("${it.itemId} is not supported")
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit()
            return@setOnItemSelectedListener true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, VideoFragment.newInstance())
                .commit()
        }

        requestPermissionsIfNeeded()
    }

    private fun requestPermissionsIfNeeded() {
        val selfPermission = checkSelfPermission(this, READ_EXTERNAL_STORAGE)
        if (selfPermission == PERMISSION_DENIED)
            requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE && grantResults.contains(PERMISSION_GRANTED)) {
            videoViewModel.loadVideo()
            audioViewModel.loadAudio()
            imagesViewModel.loadImages()
        }
    }

    companion object {
        const val REQUEST_CODE = 256
    }
}
