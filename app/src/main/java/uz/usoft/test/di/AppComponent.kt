package uz.usoft.test.di

import dagger.Component
import uz.usoft.test.ui.video.search.SearchVideoFragment
import uz.usoft.test.ui.video.watch.WatchFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, PresenterModule::class])
interface AppComponent {
    fun inject(fragment: SearchVideoFragment)
    fun inject(fragment: WatchFragment)
}