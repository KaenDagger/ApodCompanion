package io.kaendagger.apodcompanion.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.kaendagger.ui.home.ImageAdapter

@Module(includes = [PicassoModule::class])
class MainActivityModule{
    @Provides
    fun provideImageAdapter(picasso: Picasso) = ImageAdapter(picasso)
}