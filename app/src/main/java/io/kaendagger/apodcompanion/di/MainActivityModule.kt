package io.kaendagger.apodcompanion.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.kaendagger.apodcompanion.ui.home.ImageAdapter
import io.kaendagger.apodcompanion.ui.home.MainActivity

@Module(includes = [PicassoModule::class])
class MainActivityModule(private val mainActivity: MainActivity){
    @Provides
    fun provideImageAdapter(picasso: Picasso) = ImageAdapter(picasso,mainActivity)
}