package uz.usoft.test.ui.video.watch

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.navigation.fragment.navArgs
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.analytics.PlaybackStatsListener
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import uz.usoft.test.R
import uz.usoft.test.core.BaseFragment
import uz.usoft.test.core.exoplayer.ExoPlayerManager
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
        extractYoutubeUrl()
    }

    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)
        when(state) {
            ExoPlayer.STATE_IDLE -> {}
            ExoPlayer.STATE_BUFFERING -> {}
            ExoPlayer.STATE_READY -> { player.play() }
            ExoPlayer.STATE_ENDED -> {}
            else -> {}
        }
    }

    private fun extractYoutubeUrl() {
        val mExtractor = object: YouTubeExtractor(requireContext()) {
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, videoMeta: VideoMeta?) {
                playVideo(ytFiles?.get(17)!!.url)
            }
        }.extract("https://www.youtube.com/watch?v=${safeArgs.id}", true, true)
    }

    private fun playVideo(url: String) {
        binding.playerView.player = ExoPlayerManager.getSharedInstance(requireContext()).playerView.player
        ExoPlayerManager.getSharedInstance(requireContext()).playStream(url)
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        player.addListener(this)
        binding.playerView.player = player
        val uri = Uri.parse("https://www.youtube.com/watch?v=${safeArgs.id}")

        player.setMediaItem(mediaItem)
        player.playWhenReady = true
        player.seekTo(currentWindow, playbackPosition)
        player.play()
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

//    private fun hideSystemUi() {
//        binding.playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
//                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
//    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 24) {
    //        initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
      //  hideSystemUi()
        if ((Util.SDK_INT < 24 || !::player.isInitialized)) {
//            initializePlayer()
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
