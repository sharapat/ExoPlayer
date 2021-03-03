package uz.usoft.test.ui.video.watch

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import uz.usoft.test.R
import uz.usoft.test.core.BaseFragment
import uz.usoft.test.databinding.FragmentVideoBinding

class WatchFragment : BaseFragment(R.layout.fragment_video), Player.EventListener {

    private lateinit var binding: FragmentVideoBinding
    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaItem: MediaItem
    private val safeArgs: WatchFragmentArgs by navArgs()
    private var playWhenReady: Boolean = false
    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoBinding.bind(view)
    }

    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)
        when (state) {
            ExoPlayer.STATE_IDLE -> {
            }
            ExoPlayer.STATE_BUFFERING -> {
            }
            ExoPlayer.STATE_READY -> {
                player.play()
            }
            ExoPlayer.STATE_ENDED -> {
            }
            else -> {
            }
        }
    }

    private fun initializePlayer() {
        if (!::player.isInitialized) {
            player = SimpleExoPlayer.Builder(requireContext()).build()
        }
        binding.playerView.player = player
        player.addListener(this)
        mediaItem = MediaItem.fromUri(safeArgs.id)
        val dataSourceFactory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), "Player")
        )
        val extractorMediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                mediaItem
            )
        val isResuming = playbackPosition != 0L
        player.setMediaSource(extractorMediaSource, isResuming)
        player.prepare()
        player.playWhenReady = true
        if (isResuming) {
            player.seekTo(playbackPosition)
        }
    }

    private fun releasePlayer() {
        if (::player.isInitialized) {
            playWhenReady = player.playWhenReady
            playbackPosition = player.currentPosition
            currentWindow = player.currentWindowIndex
            player.removeListener(this)
            player.release()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24 || !::player.isInitialized)) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }
}
