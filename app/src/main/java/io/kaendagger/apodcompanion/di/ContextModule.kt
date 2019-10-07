package io.kaendagger.apodcompanion.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {

    private val sharedPrefName = "datePrefs"
    @Provides
    fun provideContext() = context.applicationContext

    @Provides
    fun providesSharedPrefs() = context.getSharedPreferences(sharedPrefName,Context.MODE_PRIVATE)
}