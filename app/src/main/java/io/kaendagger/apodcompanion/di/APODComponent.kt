package io.kaendagger.apodcompanion.di

import com.squareup.picasso.Picasso
import dagger.Component
import io.kaendagger.ui.home.ImageAdapter
import io.kaendagger.ui.home.MainActivityViewModel
import io.kaendagger.apodcompanion.data.APODRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [MainActivityModule::class,ViewModelModule::class])
interface APODComponent {
    fun getImageAdapter(): ImageAdapter
    fun getMaViewModel(): MainActivityViewModel
    fun getAPODRepository():APODRepository
    fun getPicasso():Picasso
}