package uz.usoft.test

import android.app.Application
import uz.usoft.test.di.AppComponent
import uz.usoft.test.di.DaggerAppComponent

class App : Application() {
        val component: AppComponent = DaggerAppComponent.builder()
            .build()
}