package io.kaendagger.apodcompanion.di

import com.squareup.picasso.Picasso
import dagger.Component
import io.kaendagger.ui.home.ImageAdapter
import io.kaendagger.ui.home.MainActivityViewModel
import io.kaendagger.apodcompanion.data.APODRepository
import io.kaendagger.ui.home.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [MainActivityModule::class,ViewModelModule::class])
interface APODComponent {

    fun injectMainActivity(mainActivity: MainActivity)

    fun getImageAdapter(): ImageAdapter

    fun getMaViewModel(): MainActivityViewModel
}