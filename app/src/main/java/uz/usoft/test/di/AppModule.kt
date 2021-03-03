package uz.usoft.test.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import uz.usoft.test.Settings
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun providesSettings(context: Context) = Settings(context)
}