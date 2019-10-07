package io.kaendagger.apodcompanion.di

import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import io.kaendagger.apodcompanion.ui.viewer.ImagePagerAdapter
import io.kaendagger.apodcompanion.ui.viewer.ViewerActivity

@Module
class ViewerActivityModule (private val viewerActivity: ViewerActivity){

    @Provides
    fun provideImagePagerAdapter(fm:FragmentManager) = ImagePagerAdapter(fm)

    @Provides
    fun provideFragmentManager() = viewerActivity.supportFragmentManager
}