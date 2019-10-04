package io.kaendagger.apodcompanion.data.remote

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@Module
class APODModule {
    private val baseUrl = "https://api.nasa.gov"

    @Provides
    fun apodService(retrofit: Retrofit): APODApi {
        return retrofit.create(APODApi::class.java)
    }

    @Provides
    fun picasso(): Picasso {
        return Picasso.get()
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