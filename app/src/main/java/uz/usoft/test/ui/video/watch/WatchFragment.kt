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
import uz.usoft.test.App
import uz.usoft.test.R
import uz.usoft.test.core.BaseFragment
import uz.usoft.test.databinding.FragmentVideoBinding
import javax.inject.Inject

class WatchFragment : BaseFragment(R.layout.fragment_video), WatchView {

    private lateinit var binding: FragmentVideoBinding
    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaItem: MediaItem
    private val safeArgs: WatchFragmentArgs by navArgs()
    private var playWhenReady: Boolean = false
    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    @Inject
    lateinit var presenter: WatchPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).component.inject(this)
        binding = FragmentVideoBinding.bind(view)
        presenter.init(this)
    }

    private fun initializePlayer() {
        if (!::player.isInitialized) {
            player = SimpleExoPlayer.Builder(requireContext()).build()
        }
        binding.playerView.player = player
        mediaItem = MediaItem.fromUri(safeArgs.url)
        val dataSourceFactory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), "Player")
        )
        val extractorMediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                mediaItem
            )
        presenter.getPlaybackPosition(safeArgs.url)
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
            presenter.setPlaybackPosition(safeArgs.url, playbackPosition)
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

    override fun setPlaybackPosition(position: Long) {
        playbackPosition = position
    }
}
