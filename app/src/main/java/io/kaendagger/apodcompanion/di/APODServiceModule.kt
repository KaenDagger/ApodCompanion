package io.kaendagger.apodcompanion.di

import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.kaendagger.apodcompanion.data.remote.APODApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class APODServiceModule {
    private val baseUrl = "https://api.nasa.gov"

    @Provides
    fun apodService(retrofit: Retrofit): APODApi {
        return retrofit.create(APODApi::class.java)
    }

    @Provides
    fun retrofit(moshiConverterFactory: MoshiConverterFactory, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    fun moshi() = Moshi.Builder().build()

    @Provides
    fun moshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }
}