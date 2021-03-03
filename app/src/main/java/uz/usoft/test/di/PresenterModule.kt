package uz.usoft.test.di

import dagger.Module
import dagger.Provides
import uz.usoft.test.data.ApiService
import uz.usoft.test.ui.video.search.SearchVideoPresenter
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providesSearchVideoPresenter(apiService: ApiService) = SearchVideoPresenter(apiService)
}