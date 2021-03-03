package uz.usoft.test.di

import dagger.Component
import uz.usoft.test.ui.video.search.SearchVideoFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, PresenterModule::class])
interface AppComponent {
    fun inject(fragment: SearchVideoFragment)
}