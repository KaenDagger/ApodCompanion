package io.kaendagger.apodcompanion.di

import dagger.Component
import io.kaendagger.apodcompanion.ui.home.ImageAdapter
import io.kaendagger.apodcompanion.ui.APODViewModel
import io.kaendagger.apodcompanion.ui.home.MainActivity
import io.kaendagger.apodcompanion.ui.viewer.ViewerActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [MainActivityModule::class,ViewModelModule::class])
interface APODComponent {

    fun injectMainActivity(mainActivity: MainActivity)

    fun getImageAdapter(): ImageAdapter

    fun getViewModel(): APODViewModel
}