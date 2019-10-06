package io.kaendagger.apodcompanion.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.kaendagger.apodcompanion.data.APODRepository
import io.kaendagger.apodcompanion.data.database.APODDao
import io.kaendagger.apodcompanion.data.database.APODDatabase
import io.kaendagger.apodcompanion.data.remote.APODApi

@Module(includes = [APODServiceModule::class,ContextModule::class])
class APODRoomModule (application: Application){

    private val apodDB = Room.databaseBuilder(application,APODDatabase::class.java,"apod-db").build()

    @Provides
    fun provideApodDatabase() = apodDB

    @Provides
    fun providesApodDao() = apodDB.apodDao()

    @Provides
    fun providesApodRepository(
        apodService:APODApi,
        apodDao: APODDao,
        picasso: Picasso,
        context: Context
    ) = APODRepository(apodService, apodDao, picasso, context)
}