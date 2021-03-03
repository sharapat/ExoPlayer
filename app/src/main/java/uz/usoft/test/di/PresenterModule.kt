package uz.usoft.test.di

import dagger.Module
import dagger.Provides
import uz.usoft.test.Settings
import uz.usoft.test.data.ApiService
import uz.usoft.test.ui.video.search.SearchVideoPresenter
import uz.usoft.test.ui.video.watch.WatchPresenter
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providesSearchVideoPresenter(apiService: ApiService) = SearchVideoPresenter(apiService)

    @Provides
    @Singleton
    fun providesWatchPresenter(settings: Settings) = WatchPresenter(settings)
}