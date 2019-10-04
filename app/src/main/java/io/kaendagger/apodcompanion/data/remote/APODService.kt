package io.kaendagger.apodcompanion.data.remote

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.prefs.PreferencesFactory

@Module
class APODService {
    private val baseUrl = "https://api.nasa.gov/planetary/apod/"

    @Provides
    fun retrofit(moshiConverterFactory: MoshiConverterFactory,moshi: Moshi):Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    fun moshi() = Moshi.Builder().build()

    @Provides
    fun moshiConverterFactory(moshi: Moshi):MoshiConverterFactory{
        return MoshiConverterFactory.create(moshi)
    }
}