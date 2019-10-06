package io.kaendagger.apodcompanion.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.kaendagger.apodcompanion.data.model.ApodOffline


@Database(entities = [ApodOffline::class],version = 1)
abstract class APODDatabase:RoomDatabase(){
    abstract fun apodDao() :APODDao
}