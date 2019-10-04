package io.kaendagger.apodcompanion

import com.squareup.picasso.Picasso
import dagger.Component
import io.kaendagger.apodcompanion.data.remote.APODApi
import io.kaendagger.apodcompanion.data.remote.APODModule

@Component(modules = [APODModule::class])
interface APODComponent {
    fun getAPODService():APODApi
    fun getPicasso():Picasso
}