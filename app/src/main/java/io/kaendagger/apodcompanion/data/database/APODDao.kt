package io.kaendagger.apodcompanion.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.kaendagger.apodcompanion.data.model.Apod
import io.kaendagger.apodcompanion.data.model.ApodOffline

@Dao
interface APODDao {
    @Query("SELECT * FROM apod")
    suspend fun getPastAPODs():List<ApodOffline>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAPODOffline(apodOffline: ApodOffline)
}