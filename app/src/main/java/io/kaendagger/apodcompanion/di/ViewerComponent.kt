package io.kaendagger.apodcompanion.di

import dagger.Component
import io.kaendagger.apodcompanion.ui.APODViewModel
import io.kaendagger.apodcompanion.ui.viewer.ViewerActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewerActivityModule::class,ViewModelModule::class,PicassoModule::class])
interface ViewerComponent {

    fun injectViewerActivity(viewerActivity: ViewerActivity)

    fun getViewModel(): APODViewModel
}