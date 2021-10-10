package com.example.someapp


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

//    lateinit var applicationComponent: ApplicationComponent
//
//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        applicationComponent = ApplicationComponentFactory.create(this)
//        return applicationComponent
//    }

    override fun onCreate() {
        super.onCreate()
//        applicationComponent.inject(this)
    }
}
