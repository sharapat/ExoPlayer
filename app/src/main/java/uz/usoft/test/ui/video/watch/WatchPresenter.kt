package uz.usoft.test.ui.video.watch

import uz.usoft.test.Settings

class WatchPresenter(private val settings: Settings) {

    private lateinit var view: WatchView
    fun init(view: WatchView) {
        this.view = view
    }

    fun setPlaybackPosition(url: String, position: Long) {
        settings.setPlaybackPosition(url, position)
    }

    fun getPlaybackPosition(url: String) {
        view.setPlaybackPosition(settings.getPlaybackPosition(url))
    }
}